package com.example.moviemenaceapimovies.domain;

import java.util.List;

public class Movie {
    private int id;
    private String title;
    private String overview;
    private String release_date;
    private boolean adult;
    private String status;
    private double popularity;
    private int runtime;

    public Movie(int id, String title, String overview, String release_date, boolean adult,
                 String status, double popularity, int runtime) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
        this.adult = adult;
        this.status = status;
        this.popularity = popularity;
        this.runtime = runtime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public boolean isAdult() {
        return adult;
    }

    public int getAdult(){
        if(adult){
            return 1;
        } else {
            return 0;
        }
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", overview='" + overview + '\'' +
                ", release_date='" + release_date + '\'' +
                ", adult=" + adult +
                ", status='" + status + '\'' +
                ", popularity=" + popularity +
                '}';
    }
}
