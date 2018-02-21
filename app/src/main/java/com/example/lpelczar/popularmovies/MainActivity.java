package com.example.lpelczar.popularmovies;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.lpelczar.popularmovies.models.Movie;

import java.util.ArrayList;
import java.util.List;

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

    private final String API_KEY = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new MoviesAdapter(this, this);
        recyclerView.setAdapter(adapter);
        fetchMoviesDataFromDB();
    }

    private void fetchMoviesDataFromDB() {
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
        intent.putExtra(DetailActivity.EXTRA_MOVIE, adapter.getMovieList().get(position));
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
                    fetchMoviesDataFromDB();
                    return true;
            case R.id.action_sort_top_rated:
                    sort_order = SORT_BY_TOP_RATED;
                    fetchMoviesDataFromDB();
                    return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
