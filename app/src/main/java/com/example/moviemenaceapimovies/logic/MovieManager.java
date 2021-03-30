package com.example.moviemenaceapimovies.logic;

import android.util.Log;

import com.example.moviemenaceapimovies.datalayer.API.MovieAPI;
import com.example.moviemenaceapimovies.domain.Movie;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieManager implements Callback<Movie> {

    private final String TAG = this.getClass().getSimpleName();
    private static final String BASE_URL = MovieIDManager.BASE_URL;

    private MovieControllerListener listener;

    List<Movie> movies = new ArrayList<>();

    private final Retrofit retrofit;
    private final Gson gson;
    private final MovieAPI movieAPI;

    public MovieManager(MovieControllerListener listener) {
        this.listener = listener;

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

    @Override
    public void onResponse(Call<Movie> call, Response<Movie> response) {
        Log.d(TAG, "onResponse() status code: " + response.code());

        if (response.isSuccessful()) {
            Log.d(TAG, "Response: " + response.body());

            Movie movie = response.body();
            this.movies.add(movie);
            if (this.movies.size() == 60) {
                listener.onMovieDetailsAvailable(this.movies);
            }
        } else {
            Log.e(TAG, "Not successful! Message: " + response.message());
        }
    }

    @Override
    public void onFailure(Call<Movie> call, Throwable t) {
        Log.e(TAG, "onFailure" + t.getMessage());
    }


    public interface MovieControllerListener {
        void onMovieDetailsAvailable(List<Movie> movie);
    }


}
