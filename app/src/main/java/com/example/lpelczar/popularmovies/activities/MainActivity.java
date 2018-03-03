package com.example.lpelczar.popularmovies.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.lpelczar.popularmovies.BuildConfig;
import com.example.lpelczar.popularmovies.services.MoviesAPIService;
import com.example.lpelczar.popularmovies.adapters.MoviesAdapter;
import com.example.lpelczar.popularmovies.R;
import com.example.lpelczar.popularmovies.models.Movie;
import com.example.lpelczar.popularmovies.models.Review;
import com.example.lpelczar.popularmovies.models.Video;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.ListItemClickListener {

    private final String SORT_BY_POPULAR = "popular";
    private final String SORT_BY_TOP_RATED = "top_rated";
    private MoviesAdapter moviesAdapter;
    private String sort_order = SORT_BY_POPULAR;

    protected static final String API_KEY = BuildConfig.THE_MOVIE_DB_API_TOKEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        moviesAdapter = new MoviesAdapter(this, this);
        recyclerView.setAdapter(moviesAdapter);
        fetchMoviesDataFromDb();
    }

    private void fetchMoviesDataFromDb() {
        if (sort_order.equals(SORT_BY_POPULAR)) {
            getPopularMovies(getMoviesApiService());
        } else if (sort_order.equals(SORT_BY_TOP_RATED)) {
            getTopRatedMovies(getMoviesApiService());
        }
    }

    private MoviesAPIService getMoviesApiService() {
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
        return restAdapter.create(MoviesAPIService.class);
    }

    private void getPopularMovies(final MoviesAPIService service) {
        service.getPopularMovies(new Callback<Movie.MovieResult>() {
            @Override
            public void success(Movie.MovieResult movieResult, Response response) {
                moviesAdapter.setMovieList(movieResult.getResults());
                fetchTrailersFromDb();
                fetchReviewsFromDb();
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    private void getTopRatedMovies(final MoviesAPIService service) {
        service.getTopRatedMovies(new Callback<Movie.MovieResult>() {
            @Override
            public void success(Movie.MovieResult movieResult, Response response) {
                moviesAdapter.setMovieList(movieResult.getResults());
                fetchTrailersFromDb();
                fetchReviewsFromDb();
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    private void fetchTrailersFromDb() {
        for (Movie m : moviesAdapter.getMovieList()) {
            m.setVideos(new ArrayList<Video>());
            getMovieTrailers(m, getMoviesApiService());
        }
    }

    private void getMovieTrailers(final Movie movie, MoviesAPIService service) {
        service.getVideosByMovieId(movie.getId(), new retrofit.Callback<Video.VideoResult>() {
            @Override
            public void success(Video.VideoResult videoResult, Response response) {
                for (Video v : videoResult.getResults()) {
                    if (v.getType().equals(getString(R.string.trailer))) {
                        movie.addVideo(v);
                    }
                }
            }
            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
            }
        });
    }

    private void fetchReviewsFromDb() {
        for (Movie m : moviesAdapter.getMovieList()) {
            m.setReviews(new ArrayList<Review>());
            getMovieReviews(m, getMoviesApiService());
        }
    }

    private void getMovieReviews(final Movie movie, MoviesAPIService service) {
        service.getReviewsByMovieId(movie.getId(), new retrofit.Callback<Review.ReviewResult>() {
            @Override
            public void success(Review.ReviewResult reviewResult, Response response) {
                movie.setReviews(reviewResult.getResults());
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
        Movie movie = moviesAdapter.getMovieList().get(position);
        intent.putExtra(DetailActivity.EXTRA_MOVIE, movie);
        startActivity(intent);
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
