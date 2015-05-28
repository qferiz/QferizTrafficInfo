package com.qferiz.qferiztrafficinfo.extras;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

/**
 * Created by Qferiz on 15/04/2015.
 */
public class MovieSorter {
    public void sortMoviesByName(ArrayList<Movie> movies) {
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                return lhs.getTitle().compareTo(rhs.getTitle());
            }
        });
    }

    public void sortMoviesByDate(ArrayList<Movie> movies) {
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                Date lhsDate = lhs.getReleaseDateTheater();
                Date rhsDate = rhs.getReleaseDateTheater();
                if (lhsDate != null && rhsDate != null) {
                    return rhsDate.compareTo(lhsDate);
                } else {
                    return 0;
                }
            }
        });
    }

    public void sortMoviesByRating(ArrayList<Movie> movies) {
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie lhs, Movie rhs) {
                //return lhs.getReleaseDateTheater().compareTo(rhs.getReleaseDateTheater());
                int ratingLhs = lhs.getAudienceScore();
                int ratingRhs = rhs.getAudienceScore();
                if (ratingLhs < ratingRhs) {
                    return 1;
                } else if (ratingLhs > ratingRhs) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
    }


}
