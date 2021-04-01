package com.example.moviemenaceapimovies.datalayer.API;

import com.example.moviemenaceapimovies.domain.TranslatedMovie;
import com.example.moviemenaceapimovies.domain.Movie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieAPI {

    String API_KEY = "e26a8690ca9f73d9faf6d79bbbc0f4f1";

    @GET("trending/movie/week?api_key=" + API_KEY)
    Call<MovieIDApiResponse> loadTrendingMoviesByWeek(@Query("page") int page);

    @GET("movie/{MOVIE_ID}?api_key=" + API_KEY)
    Call<Movie> getMovieDetails(@Path("MOVIE_ID") String ID);

    @GET("movie/{MOVIE_ID}?api_key=" + API_KEY + "&language=nl-NL")
    Call<TranslatedMovie> getDutchMovieDetails(@Path("MOVIE_ID") String ID);
}
