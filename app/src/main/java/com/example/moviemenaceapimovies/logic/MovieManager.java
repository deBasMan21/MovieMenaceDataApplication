package com.example.moviemenaceapimovies.logic;

import com.example.moviemenaceapimovies.datalayer.API.MovieAPI;
import com.example.moviemenaceapimovies.datalayer.SQL.MovieSQL;
import com.example.moviemenaceapimovies.domain.TranslatedMovie;
import com.example.moviemenaceapimovies.domain.Movie;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;

public class MovieManager {

    private final String TAG = this.getClass().getSimpleName();

    ArrayList<Movie> movies = new ArrayList<>();
    ArrayList<TranslatedMovie> translatedMovies = new ArrayList<>();

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
        Call<TranslatedMovie> call = movieAPI.getDutchMovieDetails(ID);
        TranslatedMovie translatedMovie;
        translatedMovie = call.execute().body();
        this.translatedMovies.add(translatedMovie);
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

    public void addTranslatedMovieToDb(TranslatedMovie translatedMovie) {
        movieSQL.addDutchTranslatedMoviesToDb(translatedMovie);
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public ArrayList<TranslatedMovie> getTranslatedMovies() {
        return translatedMovies;
    }

    public void addAvansEndgame() {
        movieSQL.addAvansEndgame();
    }
}
