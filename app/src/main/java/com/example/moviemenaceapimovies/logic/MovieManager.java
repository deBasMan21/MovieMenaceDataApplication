package com.example.moviemenaceapimovies.logic;

import android.util.Log;

import com.example.moviemenaceapimovies.datalayer.API.MovieAPI;
import com.example.moviemenaceapimovies.datalayer.SQL.MovieSQL;
import com.example.moviemenaceapimovies.domain.Movie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieManager implements Callback<Movie> {

    private final String TAG = this.getClass().getSimpleName();

    ArrayList<Movie> movies = new ArrayList<>();
    ArrayList<String> movieIDs = new ArrayList<>();

    private final MovieSQL movieSQL;

    public MovieManager(MovieSQL movieSQL) {
        this.movieSQL = movieSQL;
    }

    public void getMovieDetails(String ID) throws IOException {
        MovieAPI movieAPI = ServiceGenerator.createService(MovieAPI.class);
        Call<Movie> call = movieAPI.getMovieDetails(ID);
        Movie movie;
        movie = call.execute().body();
        this.movies.add(movie);
    }

    public void addMoviesToDb(ArrayList<Movie> movies) {
        movieSQL.addMoviesToDb(movies);
    }

    public void removeMoviesFromDb() {
        movies.clear();
        movieSQL.removeMoviesFromDb();
    }

    public boolean areMoviesInDb() {
        return movieSQL.areMoviesInDb();
    }

    public ArrayList<Movie> getMoviesFromDb() {
        return movieSQL.getMoviesFromDb();
    }

    public boolean checkSize() {
        return this.movies.size() == 60;
    }

    @Override
    public void onResponse(Call<Movie> call, Response<Movie> response) {
        Log.d(TAG, "onResponse() status code: " + response.code());

        if (response.isSuccessful()) {
            Log.d(TAG, "Response: " + response.body());

            Movie movie = response.body();
            this.movies.add(movie);
        } else {
            Log.e(TAG, "Not successful! Message: " + response.message());
        }
    }

    @Override
    public void onFailure(Call<Movie> call, Throwable t) {
        Log.e(TAG, "onFailure" + t.getMessage());
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }
}
