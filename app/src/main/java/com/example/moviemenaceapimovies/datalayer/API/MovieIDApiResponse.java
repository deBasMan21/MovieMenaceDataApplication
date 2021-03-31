package com.example.moviemenaceapimovies.datalayer.API;

import com.example.moviemenaceapimovies.domain.Movie;
import com.example.moviemenaceapimovies.domain.MovieID;

import java.util.ArrayList;
import java.util.List;

public class MovieIDApiResponse {

    private int page;
    private ArrayList<MovieID> results;

    public MovieIDApiResponse(int page, ArrayList<MovieID> results) {
        this.page = page;
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public ArrayList<MovieID> getResults() {
        return results;
    }

    public void setResults(ArrayList<MovieID> results) {
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
