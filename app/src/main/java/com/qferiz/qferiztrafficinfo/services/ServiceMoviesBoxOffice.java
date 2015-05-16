package com.qferiz.qferiztrafficinfo.services;

import com.qferiz.qferiztrafficinfo.callbacks.BoxOfficeMoviesLoadedListener;
import com.qferiz.qferiztrafficinfo.extras.Movie;
import com.qferiz.qferiztrafficinfo.logging.L;
import com.qferiz.qferiztrafficinfo.task.TaskLoadMoviesBoxOffice;

import java.util.ArrayList;

import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;

/**
 * Created by Qferiz on 19/04/2015.
 */
public class ServiceMoviesBoxOffice extends JobService implements BoxOfficeMoviesLoadedListener {
    private JobParameters jobParameters;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        L.t(this, "onStartJob");
        this.jobParameters = jobParameters;
        new TaskLoadMoviesBoxOffice(this).execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        L.t(this, "onStopJob");
        return false;
    }

    @Override
    public void onBoxOfficeMoviesLoaded(ArrayList<Movie> listMovies) {
        L.t(this, "onBoxOfficeMoviesLoaded");
        jobFinished(jobParameters, false);

    }

}

