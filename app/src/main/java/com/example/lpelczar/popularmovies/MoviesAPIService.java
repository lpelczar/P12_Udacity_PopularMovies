package com.example.lpelczar.popularmovies;

import com.example.lpelczar.popularmovies.models.Movie;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by lpelczar on 17.02.18.
 */

public interface MoviesAPIService {
    @GET("/movie/popular")
    void getPopularMovies(Callback<Movie.MovieResult> cb);
}
