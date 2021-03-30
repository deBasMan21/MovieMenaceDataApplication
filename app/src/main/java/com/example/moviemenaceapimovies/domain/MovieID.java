package com.example.moviemenaceapimovies.domain;

public class MovieID {

    private String title;
    private String id;
    private boolean added = false;

    public MovieID(String title, String id) {
        this.title = title;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isAdded() {
        return added;
    }

    public void setAdded(boolean added) {
        this.added = added;
    }

    @Override
    public String toString() {
        return "MovieID{" +
                "title='" + title + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
