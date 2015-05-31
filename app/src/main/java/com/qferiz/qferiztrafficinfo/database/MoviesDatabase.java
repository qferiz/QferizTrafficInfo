package com.qferiz.qferiztrafficinfo.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.qferiz.qferiztrafficinfo.extras.Movie;
import com.qferiz.qferiztrafficinfo.logging.L;

import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Qferiz on 21/04/2015.
 */

public class MoviesDatabase {
    public static final int BOX_OFFICE = 0;
    public static final int UPCOMING = 1;
    private MoviesHelper mHelper;
    private SQLiteDatabase mDatabase;

    public MoviesDatabase(Context context) {
        mHelper = new MoviesHelper(context);
        mDatabase = mHelper.getWritableDatabase();
    }

    public void insertMovies(int table, ArrayList<Movie> listMovies, boolean clearPrevious) {
        if (clearPrevious) {
            deleteMovies(table);
        }

        // create a sql prepared statement
        String sql = "INSERT INTO " + (table == BOX_OFFICE ? MoviesHelper.TABLE_BOX_OFFICE : MoviesHelper.TABLE_UPCOMING) + " VALUES (?,?,?,?,?,?,?,?,?,?);";
        // Compile the statement and start a transaction
        SQLiteStatement statement = mDatabase.compileStatement(sql);
        mDatabase.beginTransaction();
        for (int i = 0; i < listMovies.size(); i++) {
            Movie currentMovie = listMovies.get(i);
            statement.clearBindings();
            // For a given coloumn index, simply bind the data to be put inside that index
            statement.bindString(2, currentMovie.getTitle());
            statement.bindLong(3, currentMovie.getReleaseDateTheater() == null ? -1 : currentMovie.getReleaseDateTheater().getTime());
            statement.bindLong(4, currentMovie.getAudienceScore());
            statement.bindString(5, currentMovie.getSynopsis());
            statement.bindString(6, currentMovie.getUrlThumbnail());
            statement.bindString(7, currentMovie.getUrlSelf());
            statement.bindString(8, currentMovie.getUrlCast());
            statement.bindString(9, currentMovie.getUrlReviews());
            statement.bindString(10, currentMovie.getUrlSimilar());
            L.m("Inserting entry " + i);
            statement.execute();
        }

        // Set the Transaction as Succesfull and end the transaction
        L.m("inserting entries " + listMovies.size() + new Date(System.currentTimeMillis()));
        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
    }

    public ArrayList<Movie> readMovies(int table) {
        ArrayList<Movie> listMovies = new ArrayList<>();

        // Get a list of coloumn to be retrieved, we need all of them
        String[] columns = {MoviesHelper.COLUMN_UID,
                MoviesHelper.COLUMN_TITLE,
                MoviesHelper.COLUMN_RELEASE_DATE,
                MoviesHelper.COLUMN_AUDIENCE_SCORE,
                MoviesHelper.COLUMN_SYNOPSIS,
                MoviesHelper.COLUMN_URL_THUMBNAIL,
                MoviesHelper.COLUMN_URL_SELF,
                MoviesHelper.COLUMN_URL_CAST,
                MoviesHelper.COLUMN_URL_REVIEWS,
                MoviesHelper.COLUMN_URL_SIMILAR
        };

        Cursor mCursor = mDatabase.query((table == BOX_OFFICE ? MoviesHelper.TABLE_BOX_OFFICE : MoviesHelper.TABLE_UPCOMING), columns, null, null, null, null, null);
        if (mCursor != null && mCursor.moveToFirst()) {
            L.m("loading entries " + mCursor.getCount() + new Date(System.currentTimeMillis()));
            do {
                // Create a new movie object and retrieve the data from the mCursor to be stored in this movie object
                Movie mMovie = new Movie();
                // Each step is a 2 part process, find the index of the columns first, find the data of that columns using
                // that index and finally set our a blank mMovie object to contain our data
                mMovie.setTitle(mCursor.getString(mCursor.getColumnIndex(MoviesHelper.COLUMN_TITLE)));
                long releaseDateMilliSeconds = mCursor.getLong(mCursor.getColumnIndex(MoviesHelper.COLUMN_RELEASE_DATE));
                mMovie.setReleaseDateTheater(releaseDateMilliSeconds != -1 ? new Date(releaseDateMilliSeconds) : null);
                mMovie.setAudienceScore(mCursor.getInt(mCursor.getColumnIndex(MoviesHelper.COLUMN_AUDIENCE_SCORE)));
                mMovie.setSynopsis(mCursor.getString(mCursor.getColumnIndex(MoviesHelper.COLUMN_SYNOPSIS)));
                mMovie.setUrlThumbnail(mCursor.getString(mCursor.getColumnIndex(MoviesHelper.COLUMN_URL_THUMBNAIL)));
                mMovie.setUrlSelf(mCursor.getString(mCursor.getColumnIndex(MoviesHelper.COLUMN_URL_SELF)));
                mMovie.setUrlCast(mCursor.getString(mCursor.getColumnIndex(MoviesHelper.COLUMN_URL_CAST)));
                mMovie.setUrlReviews(mCursor.getString(mCursor.getColumnIndex(MoviesHelper.COLUMN_URL_REVIEWS)));
                mMovie.setUrlSimilar(mCursor.getString(mCursor.getColumnIndex(MoviesHelper.COLUMN_URL_SIMILAR)));
                // Add the movie to the list of movie object which we plan to return
                L.m("Getting movie object " + mMovie);
                listMovies.add(mMovie);

            }
            while (mCursor.moveToNext());
        }
        return listMovies;
    }

    private void deleteMovies(int table) {
//        mDatabase.delete(MoviesHelper.TABLE_BOX_OFFICE, null, null);
        mDatabase.delete((table == BOX_OFFICE ? MoviesHelper.TABLE_BOX_OFFICE : MoviesHelper.TABLE_UPCOMING), null, null);
    }

    private static class MoviesHelper extends SQLiteOpenHelper {
        private Context mContext;
        private static final String DB_NAME = "movies_db";
        private static final int DB_VERSION = 1;
        public static final String TABLE_UPCOMING = "movies_upcoming";
        public static final String TABLE_BOX_OFFICE = "movies_box_office";
        public static final String COLUMN_UID = "_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_AUDIENCE_SCORE = "audience_score";
        public static final String COLUMN_SYNOPSIS = "synopsis";
        public static final String COLUMN_URL_THUMBNAIL = "url_thumbnail";
        public static final String COLUMN_URL_SELF = "url_self";
        public static final String COLUMN_URL_CAST = "url_cast";
        public static final String COLUMN_URL_REVIEWS = "url_reviews";
        public static final String COLUMN_URL_SIMILAR = "url_similar";
        private static final String CREATE_TABLE_BOX_OFFICE = "CREATE TABLE " + TABLE_BOX_OFFICE + " (" +
                COLUMN_UID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_RELEASE_DATE + " INTEGER," +
                COLUMN_AUDIENCE_SCORE + " INTEGER," +
                COLUMN_SYNOPSIS + " TEXT," +
                COLUMN_URL_THUMBNAIL + " TEXT," +
                COLUMN_URL_SELF + " TEXT," +
                COLUMN_URL_CAST + " TEXT," +
                COLUMN_URL_REVIEWS + " TEXT," +
                COLUMN_URL_SIMILAR + " TEXT" +
                ");";
        private static final String CREATE_TABLE_UPCOMING = "CREATE TABLE " + TABLE_UPCOMING + " (" +
                COLUMN_UID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TITLE + " TEXT," +
                COLUMN_RELEASE_DATE + " INTEGER," +
                COLUMN_AUDIENCE_SCORE + " INTEGER," +
                COLUMN_SYNOPSIS + " TEXT," +
                COLUMN_URL_THUMBNAIL + " TEXT," +
                COLUMN_URL_SELF + " TEXT," +
                COLUMN_URL_CAST + " TEXT," +
                COLUMN_URL_REVIEWS + " TEXT," +
                COLUMN_URL_SIMILAR + " TEXT" +
                ");";


        public MoviesHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            mContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE_BOX_OFFICE);
                db.execSQL(CREATE_TABLE_UPCOMING);
                L.m("Create Table Box Office + Table Upcoming executed");
            } catch (SQLiteException exeption) {
                L.t(mContext, exeption + "");
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                L.m("Upgrade Table Box Office + Table Upcoming Executed");
                db.execSQL(" DROP TABLE " + TABLE_BOX_OFFICE + " IF EXIST;");
                db.execSQL(" DROP TABLE " + TABLE_UPCOMING + " IF EXISTS;");
                onCreate(db);
            } catch (SQLiteException exeption) {
                L.t(mContext, exeption + "");
            }

        }
    }
}
