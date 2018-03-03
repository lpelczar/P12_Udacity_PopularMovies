package com.example.lpelczar.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lpelczar.popularmovies.data.MoviesContract.MoviesEntry;

/**
 * Created by lpelczar on 03.03.18.
 */

public class MoviesDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int VERSION = 1;

    MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE "  + MoviesEntry.TABLE_NAME + " (" +
                MoviesEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                MoviesEntry.COLUMN_TITLE + " TEXT, " +
                MoviesEntry.COLUMN_POSTER + " TEXT, " +
                MoviesEntry.COLUMN_AVERAGE_VOTE + " INTEGER, " +
                MoviesEntry.COLUMN_PLOT + " TEXT, " +
                MoviesEntry.COLUMN_VIDEOS + " BYTE, " +
                MoviesEntry.COLUMN_REVIEWS + " BYTE);";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MoviesEntry.TABLE_NAME);
        onCreate(db);
    }
}
