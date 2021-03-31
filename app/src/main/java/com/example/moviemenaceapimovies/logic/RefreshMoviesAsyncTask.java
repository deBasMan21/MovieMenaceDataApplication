package com.example.moviemenaceapimovies.logic;

import android.os.AsyncTask;
import android.util.Log;

import com.example.moviemenaceapimovies.datalayer.SQL.DatabaseConnection;
import com.example.moviemenaceapimovies.datalayer.SQL.ViewingSQL;
import com.example.moviemenaceapimovies.domain.Movie;
import com.example.moviemenaceapimovies.domain.MovieID;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class RefreshMoviesAsyncTask extends AsyncTask<MovieManager, Void, ArrayList<Movie>> {

    private static final String TAG = RefreshMoviesAsyncTask.class.getSimpleName();

    private RefreshedMoviesListener listener;

    public RefreshMoviesAsyncTask(RefreshedMoviesListener listener) {
        this.listener = listener;
    }

    @Override
    protected ArrayList<Movie> doInBackground(MovieManager... movieManagers) {
        DatabaseConnection db = new DatabaseConnection();
        if (!db.connectionIsOpen()) {
            db.openConnection();
        }
        ArrayList<Movie> movies = new ArrayList<>();

        MovieManager movieManager = movieManagers[0];
        movieManager.removeMoviesFromDb();

        ArrayList<MovieID> movieIDS = new ArrayList<>();
        MovieIDManager movieIDManager = new MovieIDManager();
        for (int i = 1; i < 4; i++) {
            try {
                movieIDManager.loadTrendingMoviesPerWeek(i);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        movieIDS.clear();
        movieIDS.addAll(movieIDManager.getMovieIDS());

        for (MovieID movieID : movieIDS) {
            try {
                movieManager.getMovieDetails(movieID.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        while (movieManager.getMovies().size() < 60) {
            if (movieManager.getMovies().size() > 0) {
                Log.d(TAG, "Getting movies, current amount is: " + movieManager.getMovies().size());
            }
        }
        movies.addAll(movieManager.getMovies());

        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie m1, Movie m2) {
                return Double.compare(m2.getPopularity(), m1.getPopularity());
            }
        });
        movieManager.addMoviesToDb(movies);

        ViewingSQL viewingSQL = new ViewingSQL();
        ViewingManager vm = new ViewingManager();
        if(!viewingSQL.areViewingsCreated()){
            for(int i = 1; i < 8; i++){
                viewingSQL.addViewingsToDB(vm.createViewings(LocalDate.now().plusDays(i), movies));
            }
        }

        db.closeConnection();
        return movies;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        super.onPostExecute(movies);

        listener.onRefreshedMoviesAvailable(movies);
    }

    public interface RefreshedMoviesListener {
        void onRefreshedMoviesAvailable(ArrayList<Movie> movies);
    }
}
