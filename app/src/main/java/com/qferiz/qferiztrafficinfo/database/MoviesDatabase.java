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
    private MoviesHelper mHelper;
    private SQLiteDatabase mDatabase;

    public MoviesDatabase(Context context){
        mHelper = new MoviesHelper(context);
        mDatabase = mHelper.getWritableDatabase();
    }

    public void insertMoviesBoxOffice(ArrayList<Movie> listMovies, boolean clearPrevious){
        if (clearPrevious){
            deleteAll();
        }

        // create a sql prepared statement
        String sql = "INSERT INTO " + MoviesHelper.TABLE_BOX_OFFICE + " VALUES (?,?,?,?,?,?,?,?,?,?);";
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
        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
    }

    public ArrayList<Movie> getAllMoviesBoxOffice(){
        ArrayList<Movie> listMovies = new ArrayList<>();

        // Get a list of coloumn to be retrieved, we need all of them
        String[] columns = {MoviesHelper.COLOUMN_UID,
                MoviesHelper.COLOUMN_TITLE,
                MoviesHelper.COLOUMN_RELEASE_DATE,
                MoviesHelper.COLOUMN_AUDIENCE_SCORE,
                MoviesHelper.COLOUMN_SYNOPSIS,
                MoviesHelper.COLOUMN_URL_THUMBNAIL,
                MoviesHelper.COLOUMN_URL_SELF,
                MoviesHelper.COLOUMN_URL_CAST,
                MoviesHelper.COLOUMN_URL_REVIEWS,
                MoviesHelper.COLOUMN_URL_SIMILAR
        };

        Cursor cursor = mDatabase.query(MoviesHelper.TABLE_BOX_OFFICE, columns, null,null,null,null,null);
        if (cursor != null && cursor.moveToFirst()){
            do {
                // Create a new movie object and retrieve the data from the cursor to be stored in this movie object
                Movie movie = new Movie();
                // Each step is a 2 part process, find the index of the columns first, find the data of that columns using
                // that index and finally set our a blank movie object to contain our data
                movie.setTitle(cursor.getString(cursor.getColumnIndex(MoviesHelper.COLOUMN_TITLE)));
                long releaseDateMilliSeconds = cursor.getLong(cursor.getColumnIndex(MoviesHelper.COLOUMN_RELEASE_DATE));
                movie.setReleaseDateTheater(releaseDateMilliSeconds != -1 ? new Date(releaseDateMilliSeconds) : null);
                movie.setAudienceScore(cursor.getInt(cursor.getColumnIndex(MoviesHelper.COLOUMN_AUDIENCE_SCORE)));
                movie.setSynopsis(cursor.getString(cursor.getColumnIndex(MoviesHelper.COLOUMN_SYNOPSIS)));
                movie.setUrlThumbnail(cursor.getString(cursor.getColumnIndex(MoviesHelper.COLOUMN_URL_THUMBNAIL)));
                movie.setUrlSelf(cursor.getString(cursor.getColumnIndex(MoviesHelper.COLOUMN_URL_SELF)));
                movie.setUrlCast(cursor.getString(cursor.getColumnIndex(MoviesHelper.COLOUMN_URL_CAST)));
                movie.setUrlReviews(cursor.getString(cursor.getColumnIndex(MoviesHelper.COLOUMN_URL_REVIEWS)));
                movie.setUrlSimilar(cursor.getString(cursor.getColumnIndex(MoviesHelper.COLOUMN_URL_SIMILAR)));
                // Add the movie to the list of movie object which we plan to return
                L.m("Getting movie object " + movie);
                listMovies.add(movie);

            }
            while (cursor.moveToNext());
        }
        return listMovies;
    }

    private void deleteAll() {
        mDatabase.delete(MoviesHelper.TABLE_BOX_OFFICE, null, null);

    }

    private static class MoviesHelper extends SQLiteOpenHelper {
        private Context mContext;
        private static final String DB_NAME = "movies_db";
        private static final int DB_VERSION = 1;
        private static final String TABLE_BOX_OFFICE = "movies_box_office";
        private static final String COLOUMN_UID = "_id";
        private static final String COLOUMN_TITLE = "title";
        private static final String COLOUMN_RELEASE_DATE = "release_date";
        private static final String COLOUMN_AUDIENCE_SCORE = "audience_score";
        private static final String COLOUMN_SYNOPSIS = "synopsis";
        private static final String COLOUMN_URL_THUMBNAIL = "url_thumbnail";
        private static final String COLOUMN_URL_SELF = "url_self";
        private static final String COLOUMN_URL_CAST = "url_cast";
        private static final String COLOUMN_URL_REVIEWS = "url_reviews";
        private static final String COLOUMN_URL_SIMILAR = "url_similar";
        private static final String CREATE_TABLE_BOX_OFFICE = "CREATE TABLE " + TABLE_BOX_OFFICE + " (" +
                COLOUMN_UID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLOUMN_TITLE + " TEXT," +
                COLOUMN_RELEASE_DATE + " INTEGER," +
                COLOUMN_AUDIENCE_SCORE + " INTEGER," +
                COLOUMN_SYNOPSIS + " TEXT," +
                COLOUMN_URL_THUMBNAIL + " TEXT," +
                COLOUMN_URL_SELF + " TEXT," +
                COLOUMN_URL_CAST + " TEXT," +
                COLOUMN_URL_REVIEWS + " TEXT," +
                COLOUMN_URL_SIMILAR + " TEXT" +
                ");";


        public MoviesHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            mContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE_BOX_OFFICE);
                L.m("Create Table Box Office executed");
            } catch (SQLiteException exeption){
                L.t(mContext, exeption + "");
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                L.m("Upgrade Table Box Office Executed");
                db.execSQL(" DROP TABLE " + TABLE_BOX_OFFICE + " IF EXIST;");
                onCreate(db);
            } catch (SQLiteException exeption){
                L.t(mContext, exeption + "");
            }

        }
    }
}
