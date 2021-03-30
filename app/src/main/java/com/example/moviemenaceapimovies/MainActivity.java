package com.example.moviemenaceapimovies;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.moviemenaceapimovies.datalayer.SQL.DatabaseConnection;
import com.example.moviemenaceapimovies.datalayer.SQL.MovieSQL;
import com.example.moviemenaceapimovies.domain.Movie;
import com.example.moviemenaceapimovies.domain.MovieID;
import com.example.moviemenaceapimovies.logic.MovieIDManager;
import com.example.moviemenaceapimovies.logic.MovieManager;
import com.example.moviemenaceapimovies.logic.ViewingManager;
import com.example.moviemenaceapimovies.ui.MovieActivity;
import com.example.moviemenaceapimovies.ui.MovieAdapter;
import com.example.moviemenaceapimovies.ui.MovieOnClickHandler;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieIDManager.MovieIDControllerListener, MovieOnClickHandler, MovieManager.MovieControllerListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String INTENT_EXTRA_MOVIE_KEY = "movie";

    private ArrayList<MovieID> movieIDS = new ArrayList<>();
    private ArrayList<Movie> movies = new ArrayList<>();

    MovieIDManager movieIDManager = new MovieIDManager(this);
    MovieManager movieManager = new MovieManager(this);

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private TextView mAmountOfMovies;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview_movieID_list);
        mAmountOfMovies = findViewById(R.id.tv_movieID_list_size);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(linearLayoutManager);

//        for (int p = 1; p < 4; p++) {
//            movieIDManager.loadTrendingMoviesPerWeek(p);
//        }
        mMovieAdapter = new MovieAdapter(movies, this);
        mRecyclerView.setAdapter(mMovieAdapter);

        new loadIntoDatabase().execute(movies);

//        ViewingManager vm = new ViewingManager();
//        vm.createViewings(LocalDate.now());

    }

    @Override
    public void onMovieIDsAvailable(List<MovieID> movieIDS) {

        this.movieIDS.addAll(movieIDS);
        Log.d(TAG, "Movie IDs list size: " + movieIDS.size());
        for (MovieID movieID : movieIDS) {
            Log.d(TAG, movieID.toString());
            movieManager.getMovieDetails(movieID.getId());
        }
    }

    @Override
    public void onMovieDetailsAvailable(List<Movie> movies) {
        this.movies.addAll(movies);
        Collections.sort(this.movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie m1, Movie m2) {
                return Double.compare(m2.getPopularity(), m1.getPopularity());
            }
        });
        mAmountOfMovies.append(this.movies.size() + "");
        this.mMovieAdapter.notifyDataSetChanged();
        Log.d(TAG, "Movies list size: " + this.movies.size());
//        new loadIntoDatabase().execute();
    }

    @Override
    public void onMovieClick(View view, int itemIndex) {
        Movie clickedMovie = movies.get(itemIndex);
        int viewID = view.getId();

        if (viewID == R.id.movie_list_item) {
            Intent movieIntent = new Intent(MainActivity.this, MovieActivity.class);
            movieIntent.putExtra(INTENT_EXTRA_MOVIE_KEY, clickedMovie);
            startActivity(movieIntent);
        }
    }

    public class loadIntoDatabase extends AsyncTask<ArrayList<Movie>, Void, ArrayList<Movie>>{

        @Override
        protected ArrayList<Movie> doInBackground(ArrayList<Movie>... movies) {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            if(!databaseConnection.connectionIsOpen()){
                databaseConnection.openConnection();
            }
            MovieSQL movieSQL = new MovieSQL();
            movies[0] = movieSQL.getMoviesFromDb();
//            movieSQL.addMoviesToDb(movies);
            return movies[0];
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            onMovieDetailsAvailable(movies);
        }
    }
}