package com.qferiz.qferiztrafficinfo.task;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.qferiz.qferiztrafficinfo.callbacks.BoxOfficeMoviesLoadedListener;
import com.qferiz.qferiztrafficinfo.extras.Movie;
import com.qferiz.qferiztrafficinfo.extras.MovieUtils;
import com.qferiz.qferiztrafficinfo.network.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by Qferiz on 23/04/2015.
 */
public class TaskLoadMoviesBoxOffice extends AsyncTask<Void, Void, ArrayList<Movie>> {
    private BoxOfficeMoviesLoadedListener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    public TaskLoadMoviesBoxOffice(BoxOfficeMoviesLoadedListener myComponent){
        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }

    @Override
    protected ArrayList<Movie> doInBackground(Void... params) {
        ArrayList<Movie> listMovies = MovieUtils.loadBoxOfficeMovies(requestQueue);
        return listMovies;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> listMovies) {
//        super.onPostExecute(listMovies);
        if (myComponent != null){
            myComponent.onBoxOfficeMoviesLoaded(listMovies);
        }
    }
}
