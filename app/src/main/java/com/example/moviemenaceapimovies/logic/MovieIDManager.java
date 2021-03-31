package com.example.moviemenaceapimovies.logic;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.moviemenaceapimovies.datalayer.API.MovieAPI;
import com.example.moviemenaceapimovies.datalayer.API.MovieIDApiResponse;
import com.example.moviemenaceapimovies.domain.Movie;
import com.example.moviemenaceapimovies.domain.MovieID;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieIDManager implements Callback<MovieIDApiResponse> {

    private final String TAG = this.getClass().getSimpleName();
    public static final String BASE_URL = "https://api.themoviedb.org/3/";


    ArrayList<MovieID> movieIDs = new ArrayList<>();

    private final Retrofit retrofit;
    private final Gson gson;
    private final MovieAPI movieAPI;

    public MovieIDManager() {

        gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        movieAPI = retrofit.create(MovieAPI.class);
    }

    public void loadTrendingMoviesPerWeek(int page) {
        Call<MovieIDApiResponse> call = movieAPI.loadTrendingMoviesByWeek(page);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<MovieIDApiResponse> call, Response<MovieIDApiResponse> response) {
        Log.d(TAG, "onResponse() status code: " + response.code());

        if (response.isSuccessful()) {
            Log.d(TAG, "Response: " + response.body());

            List<MovieID> movieIDs = response.body().getResults();
            this.movieIDs.addAll(movieIDs);
        } else {
            Log.e(TAG, "Not successful! Message: " + response.message());
        }
    }

    @Override
    public void onFailure(@NonNull Call<MovieIDApiResponse> call, Throwable t) {
        Log.e(TAG, "onFailure" + t.getMessage());
    }

    public ArrayList<MovieID> getMovieIDS() {
        return movieIDs;
    }
}
