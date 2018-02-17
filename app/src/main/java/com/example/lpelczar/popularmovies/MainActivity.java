package com.example.lpelczar.popularmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.lpelczar.popularmovies.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        MoviesAdapter adapter = new MoviesAdapter(this);
        recyclerView.setAdapter(adapter);

        List<Movie> movies = new ArrayList<>();

        for (int i = 0; i < 25; i++) {
            movies.add(new Movie());
        }
        adapter.setMovieList(movies);
    }

}
