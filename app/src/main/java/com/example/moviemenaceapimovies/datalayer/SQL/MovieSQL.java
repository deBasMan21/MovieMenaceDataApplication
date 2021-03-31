package com.example.moviemenaceapimovies.datalayer.SQL;

import com.example.moviemenaceapimovies.domain.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieSQL extends DatabaseConnection {

    public void addMoviesToDb(ArrayList<Movie> movies) {
        try {
            openConnection();
            String SQL = "INSERT INTO Movie (Id, Title, Description, ReleaseDate, Adult, Status, " +
                    "Duration, Popularity, URL) VALUES";
            for (Movie movie : movies) {
                if (SQL.length() > 110) {
                    SQL = SQL + ",";
                }
                SQL = SQL + "(" + movie.getId() + ", '" + movie.getTitle().replace("'", " ") +
                        "', '" + movie.getOverview().replace("'", " ") + "', '" + movie.getRelease_date().toString() + "', " + movie.getAdult() + ", '" + movie.getStatus() + "', " + movie.getRuntime() + ", " + movie.getPopularity() + ", '" + movie.getUrl() + "')";
            }
            System.out.println(SQL);
            executeSQLStatement(SQL);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean areMoviesInDb() {
        try {
            openConnection();
            if (connectionIsOpen()) {
                String SQL = "SELECT * FROM Movie";

                rs = executeSQLSelectStatement(SQL);

                if (rs.next()) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Movie> getMoviesFromDb() {
        ArrayList<Movie> movies = null;
        try {
            if (connectionIsOpen()) {
                movies = new ArrayList<>();
                String SQL = "SELECT * FROM Movie ORDER BY Popularity DESC";

                rs = executeSQLSelectStatement(SQL);

                while (rs.next()) {
                    Movie movie = new Movie(rs.getInt("Id"), rs.getString("Title"), rs.getString(
                            "Description"), rs.getString("ReleaseDate"), rs.getBoolean("Adult"),
                            rs.getString("Status"), rs.getDouble("Popularity"), rs.getInt("Duration"));
                    movies.add(movie);
                }
                return movies;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

    public void removeMoviesFromDb() {
        try {
            if (connectionIsOpen()) {
                String SQL = "DELETE FROM Movie";

                executeSQLStatement(SQL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
