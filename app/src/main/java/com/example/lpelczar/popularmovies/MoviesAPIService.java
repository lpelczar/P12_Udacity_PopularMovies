package com.example.lpelczar.popularmovies;

import com.example.lpelczar.popularmovies.models.Movie;
import com.example.lpelczar.popularmovies.models.Video;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by lpelczar on 17.02.18.
 */

public interface MoviesAPIService {
    @GET("/movie/popular")
    void getPopularMovies(Callback<Movie.MovieResult> cb);

    @GET("/movie/top_rated")
    void getTopRatedMovies(Callback<Movie.MovieResult> cb);

    @GET("/movie/{id}/videos")
    void getVideosByMovieId(@Path("id") int id, Callback<Video.VideoResult> cb);
}
