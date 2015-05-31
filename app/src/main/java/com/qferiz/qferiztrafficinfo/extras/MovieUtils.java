package com.qferiz.qferiztrafficinfo.extras;

import com.android.volley.RequestQueue;
import com.qferiz.qferiztrafficinfo.database.MoviesDatabase;
import com.qferiz.qferiztrafficinfo.json.Endpoints;
import com.qferiz.qferiztrafficinfo.json.Parser;
import com.qferiz.qferiztrafficinfo.json.Requestor;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Qferiz on 23/04/2015.
 */
public class MovieUtils {
    public static ArrayList<Movie> loadBoxOfficeMovies(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestMoviesJSON(requestQueue, Endpoints.getRequestUrlBoxOfficeMovies(30));
        ArrayList<Movie> listMovies = Parser.parseMoviesJSON(response);
        MyApplication.getWritableDatabase().insertMovies(MoviesDatabase.BOX_OFFICE, listMovies, true);
        return listMovies;
    }

    public static ArrayList<Movie> loadUpcomingMovies(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestMoviesJSON(requestQueue, Endpoints.getRequestUrlUpcomingMovies(30));
        ArrayList<Movie> listMovies = Parser.parseMoviesJSON(response);
        MyApplication.getWritableDatabase().insertMovies(MoviesDatabase.UPCOMING, listMovies, true);
        return listMovies;
    }
}
