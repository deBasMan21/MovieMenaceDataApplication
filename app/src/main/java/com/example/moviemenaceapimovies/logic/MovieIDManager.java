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

public class MovieIDManager {

    private final String TAG = this.getClass().getSimpleName();
    public static final String BASE_URL = "https://api.themoviedb.org/3/";

    ArrayList<MovieID> movieIDs = new ArrayList<>();

    public void loadTrendingMoviesPerWeek(int page) throws IOException {
        MovieAPI movieAPI = ServiceGenerator.createService(MovieAPI.class);
        Call<MovieIDApiResponse> call = movieAPI.loadTrendingMoviesByWeek(page);
        MovieIDApiResponse movieIDApiResponse;
        movieIDApiResponse = call.execute().body();
        ArrayList<MovieID> movieIDS;
        movieIDS = movieIDApiResponse.getResults();
        this.movieIDs.addAll(movieIDS);
    }

    public ArrayList<MovieID> getMovieIDS() {
        return movieIDs;
    }
}
