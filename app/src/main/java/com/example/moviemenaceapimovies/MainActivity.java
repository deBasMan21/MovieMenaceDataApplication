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
import android.widget.Button;
import android.widget.TextView;

import com.example.moviemenaceapimovies.datalayer.SQL.DatabaseConnection;
import com.example.moviemenaceapimovies.datalayer.SQL.MovieSQL;
import com.example.moviemenaceapimovies.datalayer.SQL.ViewingSQL;
import com.example.moviemenaceapimovies.domain.Movie;
import com.example.moviemenaceapimovies.domain.MovieID;
import com.example.moviemenaceapimovies.logic.MovieIDManager;
import com.example.moviemenaceapimovies.logic.MovieManager;
import com.example.moviemenaceapimovies.logic.RefreshMoviesAsyncTask;
import com.example.moviemenaceapimovies.logic.ViewingManager;
import com.example.moviemenaceapimovies.ui.MovieActivity;
import com.example.moviemenaceapimovies.ui.MovieAdapter;
import com.example.moviemenaceapimovies.ui.MovieOnClickHandler;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieOnClickHandler, RefreshMoviesAsyncTask.RefreshedMoviesListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String INTENT_EXTRA_MOVIE_KEY = "movie";

    private ArrayList<Movie> movies = new ArrayList<>();

    MovieManager movieManager;

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private TextView mAmountOfMovies;
    private Button mRefreshMoviesButton;
    private Button mCreateViewingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview_movieID_list);
        mAmountOfMovies = findViewById(R.id.tv_movieID_list_size);
        mRefreshMoviesButton = findViewById(R.id.b_refresh_movies);
        mCreateViewingsButton = findViewById(R.id.b_create_viewings);

        mRefreshMoviesButton.setOnClickListener((View v) -> {
            new RefreshMoviesAsyncTask(this).execute(movieManager);
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(linearLayoutManager);

        mMovieAdapter = new MovieAdapter(movies, this);
        mRecyclerView.setAdapter(mMovieAdapter);

        movieManager = new MovieManager(new MovieSQL());

        new SQLDatabaseAsyncTask().execute(movies);

//        ViewingManager vm = new ViewingManager();
//        vm.createViewings(LocalDate.now());

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

    public void onMovieDetailsAvailable(ArrayList<Movie> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
        mAmountOfMovies.append(this.movies.size() + "");
        this.mMovieAdapter.notifyDataSetChanged();
        Log.d(TAG, "Movies list size: " + this.movies.size());
    }

    @Override
    public void onRefreshedMoviesAvailable(ArrayList<Movie> movies) {
        this.movies.clear();
        this.movies.addAll(movies);
        mAmountOfMovies.setText("Amount of movies: " + this.movies.size());
        this.mMovieAdapter.notifyDataSetChanged();
    }

    public class SQLDatabaseAsyncTask extends AsyncTask<ArrayList<Movie>, Void, ArrayList<Movie>>{

        @Override
        protected ArrayList<Movie> doInBackground(ArrayList<Movie>... moviesProvided) {
            DatabaseConnection db = new DatabaseConnection();
            if(!db.connectionIsOpen()){
                db.openConnection();
            }
            ArrayList<Movie> movies = moviesProvided[0];

            if (movieManager.areMoviesInDb()) {
                Log.d(TAG, "Loading movies from SQL database.");
                movies = movieManager.getMoviesFromDb();
            } else {
                Log.d(TAG, "Loading movies from themoviedb API");
                ArrayList<MovieID> movieIDS = new ArrayList<>();
                MovieIDManager movieIDManager = new MovieIDManager();
                for (int i = 1; i < 4; i++) {
                    movieIDManager.loadTrendingMoviesPerWeek(i);
                }
                movieIDS.addAll(movieIDManager.getMovieIDS());
                for (MovieID movieID : movieIDS) {
                    movieManager.getMovieDetails(movieID.getId());
                }
                movies.clear();
                movies.addAll(movieManager.getMovies());

                Collections.sort(movies, new Comparator<Movie>() {
                    @Override
                    public int compare(Movie m1, Movie m2) {
                        return Double.compare(m2.getPopularity(), m1.getPopularity());
                    }
                });

                movieManager.addMoviesToDb(movies);
            }
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
            onMovieDetailsAvailable(movies);
        }
    }
}