package com.qferiz.qferiztrafficinfo.callbacks;

import com.qferiz.qferiztrafficinfo.extras.Movie;

import java.util.ArrayList;

/**
 * Created by Qferiz on 23/04/2015.
 */
public interface BoxOfficeMoviesLoadedListener {
    public void onBoxOfficeMoviesLoaded(ArrayList<Movie> listMovies);
}
