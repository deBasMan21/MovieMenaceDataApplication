package com.example.moviemenaceapimovies.logic;

import android.util.Log;

import com.example.moviemenaceapimovies.datalayer.API.MovieAPI;
import com.example.moviemenaceapimovies.datalayer.SQL.MovieSQL;
import com.example.moviemenaceapimovies.domain.Movie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieManager implements Callback<Movie> {

    private final String TAG = this.getClass().getSimpleName();
    private static final String BASE_URL = MovieIDManager.BASE_URL;


    ArrayList<Movie> movies = new ArrayList<>();
    ArrayList<String> movieIDs = new ArrayList<>();

    private final MovieSQL movieSQL;
    private final Retrofit retrofit;
    private final Gson gson;
    private final MovieAPI movieAPI;

    public MovieManager(MovieSQL movieSQL) {
        this.movieSQL = movieSQL;

        gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        movieAPI = retrofit.create(MovieAPI.class);
    }

    public void getMovieDetails(String ID) {
        Call<Movie> call = movieAPI.getMovieDetails(ID);
        call.enqueue(this);
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
