package com.example.lpelczar.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.lpelczar.popularmovies.models.Movie;
import com.example.lpelczar.popularmovies.models.Video;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.ListItemClickListener {

    private final String SORT_BY_POPULAR = "popular";
    private final String SORT_BY_TOP_RATED = "top_rated";
    private MoviesAdapter adapter;
    private String sort_order = SORT_BY_POPULAR;

    private final String API_KEY = BuildConfig.THE_MOVIE_DB_API_TOKEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new MoviesAdapter(this, this);
        recyclerView.setAdapter(adapter);
        fetchMoviesDataFromDb();
    }

    private void fetchMoviesDataFromDb() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", API_KEY);
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MoviesAPIService service = restAdapter.create(MoviesAPIService.class);

        if (sort_order.equals(SORT_BY_POPULAR)) {
            getPopularMovies(service);
        } else if (sort_order.equals(SORT_BY_TOP_RATED)) {
            getTopRatedMovies(service);
        }
    }

    private void getPopularMovies(MoviesAPIService service) {
        service.getPopularMovies(new Callback<Movie.MovieResult>() {
            @Override
            public void success(Movie.MovieResult movieResult, Response response) {
                adapter.setMovieList(movieResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    private void getTopRatedMovies(MoviesAPIService service) {
        service.getTopRatedMovies(new Callback<Movie.MovieResult>() {
            @Override
            public void success(Movie.MovieResult movieResult, Response response) {
                adapter.setMovieList(movieResult.getResults());
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    @Override
    public void onListItemClick(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        Movie movie = adapter.getMovieList().get(position);

        fetchMovieTrailersFromDb(movie.getId());
        movie.setVideos(adapter.getVideoList());

        intent.putExtra(DetailActivity.EXTRA_MOVIE, movie);
        startActivity(intent);
    }

    private void fetchMovieTrailersFromDb(int movieId) {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://api.themoviedb.org/3")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addEncodedQueryParam("api_key", API_KEY);
                    }
                })
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        MoviesAPIService service = restAdapter.create(MoviesAPIService.class);

        getMovieTrailers(movieId, service);
    }

    private void getMovieTrailers(int movieId, MoviesAPIService service) {
        service.getVideosByMovieId(movieId, new Callback<Video.VideoResult>() {
            @Override
            public void success(Video.VideoResult videoResult, Response response) {
                List<Video> videos = videoResult.getResults();
                List<Video> trailers = new ArrayList<>();
                for (Video v : videos) {
                    if (v.getType().equals("Trailer")) {
                        trailers.add(v);
                    }
                }
                adapter.setVideoList(trailers);
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.action_sort_most_popular:
                    sort_order = SORT_BY_POPULAR;
                    fetchMoviesDataFromDb();
                    return true;
            case R.id.action_sort_top_rated:
                    sort_order = SORT_BY_TOP_RATED;
                    fetchMoviesDataFromDb();
                    return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
