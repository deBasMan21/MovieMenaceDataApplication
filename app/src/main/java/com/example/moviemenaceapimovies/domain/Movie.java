package com.example.moviemenaceapimovies.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Movie implements Parcelable {
    private int id;
    private String title;
    private String overview;
    private String release_date;
    private boolean adult;
    private String status;
    private double popularity;
    private int runtime;
    private String poster_path;

    public Movie(int id, String title, String overview, String release_date, boolean adult,
                 String status, double popularity, int runtime, String poster_path) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.release_date = release_date;
        this.adult = adult;
        this.status = status;
        this.popularity = popularity;
        this.runtime = runtime;
        this.poster_path = poster_path;
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

    public String getPoster_path() {
        return poster_path;
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
                ", runtime=" + runtime +
                ", poster_path='" + poster_path + '\'' +
                '}';
    }

    protected Movie(Parcel in) {
        id = in.readInt();
        title = in.readString();
        overview = in.readString();
        release_date = in.readString();
        adult = in.readByte() != 0;
        status = in.readString();
        popularity = in.readDouble();
        runtime = in.readInt();
        poster_path = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(status);
        dest.writeDouble(popularity);
        dest.writeInt(runtime);
        dest.writeString(poster_path);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
