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
import java.util.Locale;

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

        ImageView movieView = findViewById(R.id.movie_iv);
        Picasso.with(this)
                .load(movie.getPoster())
                .into(movieView);

        TextView title = findViewById(R.id.title_tv);
        title.setText(movie.getTitle());

        TextView releaseDate = findViewById(R.id.release_date_tv);
        releaseDate.setText(movie.getReleaseDate());

        TextView averageVote = findViewById(R.id.average_vote_tv);
        averageVote.setText(String.format(Locale.getDefault(),



                  "%.2f", movie.getAverageVote()));

        TextView description = findViewById(R.id.description_tv);
        description.setText(movie.getPlot());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.movie_not_available, Toast.LENGTH_SHORT).show();
    }
}
