package com.example.lpelczar.popularmovies.activities;


import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lpelczar.popularmovies.R;
import com.example.lpelczar.popularmovies.adapters.ReviewAdapter;
import com.example.lpelczar.popularmovies.adapters.TrailerAdapter;
import com.example.lpelczar.popularmovies.data.MovieContract.MovieEntry;
import com.example.lpelczar.popularmovies.models.Movie;
import com.example.lpelczar.popularmovies.models.Review;
import com.example.lpelczar.popularmovies.models.Video;
import com.example.lpelczar.popularmovies.widgets.NonScrollListView;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;


public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @SuppressWarnings("ConstantConditions")
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getIntent() == null) closeOnError();

        Movie movie;
        Bundle data = getIntent().getExtras();
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
        setTitle(movie.getTitle());

        populateUI(movie);
    }

    private void populateUI(Movie movie) {

        final ImageView movieView = findViewById(R.id.movie_iv);
        Picasso.with(this)
                .load(movie.getPosterURL())
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

        TextView releaseDate = findViewById(R.id.release_date_tv);
        releaseDate.setText(movie.getReleaseDate());

        TextView averageVote = findViewById(R.id.average_vote_tv);
        averageVote.setText(String.format(Locale.getDefault(),
                "%.2f", movie.getAverageVote()));

        TextView description = findViewById(R.id.description_tv);
        description.setText(movie.getPlot());

        populateTrailers(movie);
        populateReviews(movie);
        handleClickingFavouriteButton(movie);
    }

    private void populateTrailers(Movie movie) {
        if (!movie.getVideos().isEmpty()) {
            TextView trailersLabel = findViewById(R.id.trailers_label);
            trailersLabel.setText(R.string.trailers_label);
        }

        TrailerAdapter trailerAdapter = new TrailerAdapter(this, movie.getVideos());
        NonScrollListView trailersListView = findViewById(R.id.trailers);
        trailersListView.setAdapter(trailerAdapter);
        handleClickingOnTrailers(movie.getVideos(), trailersListView);
    }

    private void populateReviews(Movie movie) {
        if (!movie.getReviews().isEmpty()) {
            TextView reviewsLabel = findViewById(R.id.reviews_label);
            reviewsLabel.setText(R.string.reviews_label);
        }


        ReviewAdapter reviewAdapter = new ReviewAdapter(this, movie.getReviews());
        NonScrollListView reviewsListView = findViewById(R.id.reviews);
        reviewsListView.setAdapter(reviewAdapter);
        handleClickingOnReviews(movie.getReviews(), reviewsListView);
    }

    private void handleClickingFavouriteButton(final Movie movie) {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(MovieEntry.COLUMN_ID, movie.getId());
                contentValues.put(MovieEntry.COLUMN_TITLE, movie.getTitle());
                contentValues.put(MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
                contentValues.put(MovieEntry.COLUMN_POSTER, movie.getPoster());
                contentValues.put(MovieEntry.COLUMN_AVERAGE_VOTE, movie.getAverageVote());
                contentValues.put(MovieEntry.COLUMN_PLOT, movie.getPlot());
                contentValues.put(MovieEntry.COLUMN_VIDEOS, new Gson().toJson(movie.getVideos()));
                contentValues.put(MovieEntry.COLUMN_REVIEWS, new Gson().toJson(movie.getReviews()));

                Cursor cursor = getContentResolver().query(
                        MovieEntry.CONTENT_URI,
                        null,
                        MovieEntry.COLUMN_ID + "=" + movie.getId(),
                        null,
                        null);

                if (cursor.getCount() != 0) {

                    String stringId = Integer.toString(movie.getId());
                    Uri uri = MovieEntry.CONTENT_URI;
                    uri = uri.buildUpon().appendPath(stringId).build();

                    int moviesDeleted = getContentResolver().delete(uri,
                            MovieEntry.COLUMN_ID + "=" + movie.getId(), null);
                    if (moviesDeleted > 0) {
                        Toast.makeText(getBaseContext(), "Movie removed from favourites",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Uri uri = getContentResolver().insert(MovieEntry.CONTENT_URI, contentValues);

                    if (uri != null) {
                        Toast.makeText(getBaseContext(), "Movie added to favourites",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                cursor.close();
            }
        });
    }

    private void handleClickingOnTrailers(final List<Video> videos, ListView listView) {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Video video = videos.get(position);

                Intent appIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("vnd.youtube:" + video.getKey()));
                Intent webIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=" + video.getKey()));
                try {
                    getApplicationContext().startActivity(appIntent);
                } catch (ActivityNotFoundException ex) {
                    getApplicationContext().startActivity(webIntent);
                }
            }
        });
    }

    private void handleClickingOnReviews(final List<Review> reviews, ListView listView) {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Review review = reviews.get(position);
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(review.getUrl()));
                getApplicationContext().startActivity(webIntent);
            }
        });
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
