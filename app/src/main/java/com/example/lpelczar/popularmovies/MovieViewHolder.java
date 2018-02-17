package com.example.lpelczar.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by lpelczar on 17.02.18.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder
{
    private ImageView imageView;
    public MovieViewHolder(View itemView)
    {
        super(itemView);
        imageView = itemView.findViewById(R.id.movieImage);
    }

    public ImageView getImageView() {
        return imageView;
    }
}