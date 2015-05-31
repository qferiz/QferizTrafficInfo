package com.qferiz.qferiztrafficinfo.task;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;
import com.qferiz.qferiztrafficinfo.callbacks.UpcomingMoviesLoadedListener;
import com.qferiz.qferiztrafficinfo.extras.Movie;
import com.qferiz.qferiztrafficinfo.extras.MovieUtils;
import com.qferiz.qferiztrafficinfo.network.VolleySingleton;

import java.util.ArrayList;

/**
 * Created by Qferiz on 29-05-2015.
 */
public class TaskLoadUpcomingMovies extends AsyncTask<Void, Void, ArrayList<Movie>> {

    private UpcomingMoviesLoadedListener myComponent;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;


    public TaskLoadUpcomingMovies(UpcomingMoviesLoadedListener myComponent) {

        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }


    @Override
    protected ArrayList<Movie> doInBackground(Void... params) {

        ArrayList<Movie> listMovies = MovieUtils.loadUpcomingMovies(requestQueue);
        return listMovies;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> listMovies) {
        if (myComponent != null) {
            myComponent.onUpcomingMoviesLoaded(listMovies);
        }
    }
}
