package com.qferiz.qferiztrafficinfo.json;

import com.qferiz.qferiztrafficinfo.extras.Constants;
import com.qferiz.qferiztrafficinfo.extras.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.qferiz.qferiztrafficinfo.extras.Keys.EndpointBoxOffice.KEY_AUDIENCE_SCORE;
import static com.qferiz.qferiztrafficinfo.extras.Keys.EndpointBoxOffice.KEY_CAST;
import static com.qferiz.qferiztrafficinfo.extras.Keys.EndpointBoxOffice.KEY_ID;
import static com.qferiz.qferiztrafficinfo.extras.Keys.EndpointBoxOffice.KEY_LINKS;
import static com.qferiz.qferiztrafficinfo.extras.Keys.EndpointBoxOffice.KEY_MOVIES;
import static com.qferiz.qferiztrafficinfo.extras.Keys.EndpointBoxOffice.KEY_POSTERS;
import static com.qferiz.qferiztrafficinfo.extras.Keys.EndpointBoxOffice.KEY_RATINGS;
import static com.qferiz.qferiztrafficinfo.extras.Keys.EndpointBoxOffice.KEY_RELEASE_DATES;
import static com.qferiz.qferiztrafficinfo.extras.Keys.EndpointBoxOffice.KEY_REVIEWS;
import static com.qferiz.qferiztrafficinfo.extras.Keys.EndpointBoxOffice.KEY_SELF;
import static com.qferiz.qferiztrafficinfo.extras.Keys.EndpointBoxOffice.KEY_SIMILAR;
import static com.qferiz.qferiztrafficinfo.extras.Keys.EndpointBoxOffice.KEY_SYNOPSIS;
import static com.qferiz.qferiztrafficinfo.extras.Keys.EndpointBoxOffice.KEY_THEATER;
import static com.qferiz.qferiztrafficinfo.extras.Keys.EndpointBoxOffice.KEY_THUMBNAIL;
import static com.qferiz.qferiztrafficinfo.extras.Keys.EndpointBoxOffice.KEY_TITLE;

/**
 * Created by Qferiz on 24/04/2015.
 */
public class Parser {
    public static ArrayList<Movie> parseMoviesJSON(JSONObject response) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<Movie> listMovies = new ArrayList<>();
        if (response != null && response.length() > 0){

            try {

                JSONArray arrayMovies = response.getJSONArray(KEY_MOVIES);
                for (int i = 0; i < arrayMovies.length(); i++){
                    long id = -1;
                    String title = Constants.NA;
                    String releaseDates = Constants.NA;
                    int audienceScore = -1;
                    String synopsis = Constants.NA;
                    String urlThumbnail = Constants.NA;
                    String urlSelf = Constants.NA;
                    String urlCast = Constants.NA;
                    String urlReviews = Constants.NA;
                    String urlSimilar = Constants.NA;

                    JSONObject currentMovies = arrayMovies.getJSONObject(i);

                    // get the id of the current movie
                    if (Utils.contains(currentMovies, KEY_ID)) {
                        id = currentMovies.getLong(KEY_ID);
                    }


                    // get the title of the current movie
                    if (Utils.contains(currentMovies, KEY_TITLE)) {
                        title = currentMovies.getString(KEY_TITLE);
                    }

                    // get the release date of the current movie
                    if (Utils.contains(currentMovies, KEY_RELEASE_DATES)) {
                        JSONObject objectReleaseDates = currentMovies.getJSONObject(KEY_RELEASE_DATES);

                        if (Utils.contains(objectReleaseDates, KEY_THEATER)) {
                            releaseDates = objectReleaseDates.getString(KEY_THEATER);
                        }
                    }

                    // get the audience score for the current movie
                    if (Utils.contains(currentMovies, KEY_RATINGS)) {
                        JSONObject objectRatings = currentMovies.getJSONObject(KEY_RATINGS);
                        if (Utils.contains(objectRatings, KEY_AUDIENCE_SCORE)) {
                            audienceScore = objectRatings.getInt(KEY_AUDIENCE_SCORE);
                        }
                    }

                    // get the synopsis for the current movie
                    if (Utils.contains(currentMovies, KEY_SYNOPSIS)) {
                        synopsis = currentMovies.getString(KEY_SYNOPSIS);
                    }

                    // get the posters & thumbnail for the current movie
                    if (Utils.contains(currentMovies, KEY_POSTERS)) {
                        JSONObject objectPosters = currentMovies.getJSONObject(KEY_POSTERS);
                        if (Utils.contains(objectPosters, KEY_THUMBNAIL)) {
                            urlThumbnail = objectPosters.getString(KEY_THUMBNAIL);
                        }
                    }

                    // get the url of the related links
                    if (Utils.contains(currentMovies, KEY_LINKS)){
                        JSONObject objectLinks = currentMovies.getJSONObject(KEY_LINKS);
                        if (Utils.contains(objectLinks, KEY_SELF)){
                            urlSelf = objectLinks.getString(KEY_SELF);
                        }
                        if (Utils.contains(objectLinks, KEY_CAST)){
                            urlCast = objectLinks.getString(KEY_CAST);
                        }
                        if (Utils.contains(objectLinks, KEY_REVIEWS)){
                            urlReviews = objectLinks.getString(KEY_REVIEWS);
                        }
                        if (Utils.contains(objectLinks, KEY_SIMILAR)){
                            urlSimilar = objectLinks.getString(KEY_SIMILAR);
                        }
                    }

                    Movie movie = new Movie();
                    movie.setId(id);
                    movie.setTitle(title);
                    Date date = null;

                    try {
                        date = dateFormat.parse(releaseDates);
                    }catch (ParseException e){
                        //a parse exception generated here will store null in the release date, be sure to handle it

                    }

                    movie.setReleaseDateTheater(date);
                    movie.setAudienceScore(audienceScore);
                    movie.setSynopsis(synopsis);
                    movie.setUrlThumbnail(urlThumbnail);
                    movie.setUrlSelf(urlSelf);
                    movie.setUrlCast(urlCast);
                    movie.setUrlReviews(urlReviews);
                    movie.setUrlSimilar(urlSimilar);

                    if (id != -1 && !title.equals(Constants.NA)){
                        listMovies.add(movie);
                    }
                }

                //L.T(getActivity(), listMovies.toString());

            } catch (JSONException e){

            }

//                L.t(getActivity(), listMovies.size() + " rows fetched");
        }

        //L.t(getActivity(), listMovies.size() + " rows fetched");
        return listMovies;

    }
}
