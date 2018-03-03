package com.example.lpelczar.popularmovies.data;

import android.provider.BaseColumns;

/**
 * Created by lpelczar on 03.03.18.
 */

public class MoviesContract {

    public static final class MoviesEntry implements BaseColumns {
        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "poster";
        public static final String COLUMN_RELEASE_DATE = "releaseDate";
        public static final String COLUMN_POSTER = "poster";
        public static final String COLUMN_AVERAGE_VOTE = "averageVote";
        public static final String COLUMN_PLOT = "plot";
        public static final String COLUMN_VIDEOS = "videos";
        public static final String COLUMN_REVIEWS = "reviews";

    }
}
