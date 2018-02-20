package com.example.lpelczar.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lpelczar.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) closeOnError();

        Bundle data = getIntent().getExtras();
        if (data != null) {
            movie = data.getParcelable(EXTRA_MOVIE);
        } else {
            closeOnError();
            return;
        }

        populateUI(movie);

        setTitle(movie.getTitle());
    }

    private void populateUI(Movie movie) {

        TextView title = findViewById(R.id.title_tv);
        title.setText(movie.getTitle());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.movie_not_available, Toast.LENGTH_SHORT).show();
    }
}
