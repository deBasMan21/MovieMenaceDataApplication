package com.example.moviemenaceapimovies.logic;

import android.os.AsyncTask;

import com.example.moviemenaceapimovies.datalayer.SQL.ViewingSQL;
import com.example.moviemenaceapimovies.domain.Movie;

import java.time.LocalDate;
import java.util.ArrayList;

public class RefreshViewingsAsyncTask extends AsyncTask<ArrayList<Movie>, Void, Void> {

    @Override
    protected Void doInBackground(ArrayList<Movie>... arrayLists) {

        ArrayList<Movie> movies = arrayLists[0];

        ViewingSQL viewingSQL = new ViewingSQL();
        ViewingManager vm = new ViewingManager();
        if (viewingSQL.areViewingsCreated()) {
            viewingSQL.removeViewingsFromDb();
            for (int i = 1; i < 8; i++) {
                viewingSQL.addViewingsToDB(vm.createViewings(LocalDate.now().plusDays(i),
                        movies));
            }
        } else {
            for (int i = 1; i < 8; i++) {
                viewingSQL.addViewingsToDB(vm.createViewings(LocalDate.now().plusDays(i),
                        movies));
            }
        }
        return null;
    }
}
