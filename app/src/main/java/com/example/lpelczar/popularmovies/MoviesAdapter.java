package com.example.lpelczar.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import com.example.lpelczar.popularmovies.models.Movie;

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
}
