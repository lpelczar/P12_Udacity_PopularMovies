package com.example.lpelczar.popularmovies;


import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lpelczar.popularmovies.models.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @SuppressWarnings("ConstantConditions")
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) closeOnError();

        Bundle data = getIntent().getExtras();
        Movie movie;

        if (data != null) {
            movie = data.getParcelable(EXTRA_MOVIE);
        } else {
            closeOnError();
            return;
        }

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(movie.getTitle());
        collapsingToolbarLayout.setExpandedTitleColor(
                getResources().getColor(android.R.color.transparent));

        populateUI(movie);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        setTitle(movie.getTitle());
    }

    private void populateUI(Movie movie) {

        final ImageView movieView = findViewById(R.id.movie_iv);
        Picasso.with(this)
                .load(movie.getPoster())
                .into(movieView, new Callback() {

            @Override public void onSuccess() {

                Bitmap bitmap = ((BitmapDrawable) movieView.getDrawable()).getBitmap();
                Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                    public void onGenerated(Palette palette) {
                        applyPalette(palette);
                    }
                });
            }

            @Override public void onError() {}
        });

        TextView title = findViewById(R.id.title);
        title.setText(movie.getTitle());

//
//        TextView releaseDate = findViewById(R.id.release_date_tv);
//        releaseDate.setText(movie.getReleaseDate());
//
//        TextView averageVote = findViewById(R.id.average_vote_tv);
//        averageVote.setText(String.format(Locale.getDefault(),
//
//
//
//                  "%.2f", movie.getAverageVote()));
//
//        TextView description = findViewById(R.id.description_tv);
//        description.setText(movie.getPlot());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.movie_not_available, Toast.LENGTH_SHORT).show();
    }

    @Override public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        try {
            return super.dispatchTouchEvent(motionEvent);
        } catch (NullPointerException e) {
            return false;
        }
    }

    private void applyPalette(Palette palette) {
        int primaryDark = getResources().getColor(R.color.primary_dark);
        int primary = getResources().getColor(R.color.primary);
        collapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
        collapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));
        updateBackground((FloatingActionButton) findViewById(R.id.fab), palette);
        supportStartPostponedEnterTransition();
    }

    private void updateBackground(FloatingActionButton fab, Palette palette) {
        int lightVibrantColor = palette.getLightVibrantColor(
                getResources().getColor(android.R.color.white));
        int vibrantColor = palette.getVibrantColor(
                getResources().getColor(R.color.accent));

        fab.setRippleColor(lightVibrantColor);
        fab.setBackgroundTintList(ColorStateList.valueOf(vibrantColor));
    }
}
