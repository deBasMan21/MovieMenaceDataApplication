package com.example.moviemenaceapimovies.logic;

import android.util.Log;

import com.example.moviemenaceapimovies.datalayer.API.MovieAPI;
import com.example.moviemenaceapimovies.datalayer.SQL.MovieSQL;
import com.example.moviemenaceapimovies.domain.DutchTranslatedMovie;
import com.example.moviemenaceapimovies.domain.Movie;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieManager {

    private final String TAG = this.getClass().getSimpleName();

    ArrayList<Movie> movies = new ArrayList<>();
    ArrayList<DutchTranslatedMovie> dutchTranslatedMovies = new ArrayList<>();

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

    public void getDutchMovieDetails(String ID) throws IOException {
        MovieAPI movieAPI = ServiceGenerator.createService(MovieAPI.class);
        Call<DutchTranslatedMovie> call = movieAPI.getDutchMovieDetails(ID);
        DutchTranslatedMovie dutchTranslatedMovie;
        dutchTranslatedMovie = call.execute().body();
        this.dutchTranslatedMovies.add(dutchTranslatedMovie);
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

    public void addDutchTranslatedMovieToDb(DutchTranslatedMovie dutchMovie) {
        movieSQL.addDutchTranslatedMoviesToDb(dutchMovie);
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public ArrayList<DutchTranslatedMovie> getDutchTranslatedMovies() {
        return dutchTranslatedMovies;
    }
}
