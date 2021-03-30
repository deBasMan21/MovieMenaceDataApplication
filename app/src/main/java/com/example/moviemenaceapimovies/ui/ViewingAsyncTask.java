package com.example.moviemenaceapimovies.ui;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.moviemenaceapimovies.datalayer.SQL.DatabaseConnection;
import com.example.moviemenaceapimovies.datalayer.SQL.ViewingSQL;
import com.example.moviemenaceapimovies.domain.Movie;
import com.example.moviemenaceapimovies.domain.Viewing;

import java.util.ArrayList;

public class ViewingAsyncTask extends AsyncTask<Movie, Void, ArrayList<Viewing>> {

    private ViewingListener listener;

    public ViewingAsyncTask(ViewingListener listener) {
        this.listener = listener;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected ArrayList<Viewing> doInBackground(Movie... movie) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        if (!databaseConnection.connectionIsOpen()) {
            databaseConnection.openConnection();
        }
        ViewingSQL viewingSQL = new ViewingSQL();
        ArrayList<Viewing> viewings = viewingSQL.getViewingsByMovieId(movie[0].getId());
        return viewings;
    }

    @Override
    protected void onPostExecute(ArrayList<Viewing> viewings) {
        super.onPostExecute(viewings);

        listener.onViewingsAvailable(viewings);
    }

    public interface ViewingListener {
        void onViewingsAvailable(ArrayList<Viewing> viewings);
    }
}
