package com.qferiz.qferiztrafficinfo.extras;

import com.android.volley.RequestQueue;
import com.qferiz.qferiztrafficinfo.json.Endpoints;
import com.qferiz.qferiztrafficinfo.json.Parser;
import com.qferiz.qferiztrafficinfo.json.Requestor;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Qferiz on 23/04/2015.
 */
public class MovieUtils {
    public static ArrayList<Movie> loadBoxOfficeMovies(RequestQueue requestQueue){
        JSONObject response = Requestor.sendRequestBoxOfficeMovies(requestQueue, Endpoints.getRequestUrl(30));
        ArrayList<Movie> listMovies = Parser.parseJSONResponse(response);
        MyApplication.getWritableDatabase().insertMoviesBoxOffice(listMovies, true);
        return listMovies;
    }
}
