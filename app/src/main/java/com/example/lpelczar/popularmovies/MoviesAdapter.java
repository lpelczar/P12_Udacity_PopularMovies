package com.example.lpelczar.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lpelczar.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lpelczar on 17.02.18.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MovieViewHolder>  {

    private List<Movie> movieList;
    private LayoutInflater inflater;
    private Context context;

    public MoviesAdapter(Context context) {
        this.movieList = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }


    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        Picasso.with(context)
                .load(movie.getPoster())
                .placeholder(R.color.colorAccent)
                .into(holder.getImageView());
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
