package com.example.moviemenaceapimovies.datalayer.API;

import com.example.moviemenaceapimovies.domain.Movie;
import com.example.moviemenaceapimovies.domain.MovieID;

import java.util.List;

public class MovieIDApiResponse {

    private int page;
    private List<MovieID> results;

    public MovieIDApiResponse(int page, List<MovieID> results) {
        this.page = page;
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<MovieID> getResults() {
        return results;
    }

    public void setResults(List<MovieID> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "MovieApiResponse{" +
                "page=" + page +
                ", results=" + results +
                '}';
    }
}
