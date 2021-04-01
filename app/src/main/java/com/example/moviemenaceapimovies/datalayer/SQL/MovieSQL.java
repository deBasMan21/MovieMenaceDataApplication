package com.example.moviemenaceapimovies.datalayer.SQL;

import com.example.moviemenaceapimovies.domain.TranslatedMovie;
import com.example.moviemenaceapimovies.domain.Movie;

import java.util.ArrayList;

public class MovieSQL extends DatabaseConnection {

    public void addMoviesToDb(ArrayList<Movie> movies) {
        try {
            openConnection();
            StringBuilder SQLBuilder = new StringBuilder("INSERT INTO Movie (Id, Title, " +
                    "Description, ReleaseDate, Adult, Status, " +
                    "Duration, Popularity, URL) VALUES");
            for (Movie movie : movies) {
                if (SQLBuilder.length() > 110) {
                    SQLBuilder.append(",");
                }
                SQLBuilder.append("(").append(movie.getId()).append(", '")
                        .append(movie.getTitle().replace("'", " ")).append("', '")
                        .append(movie.getOverview().replace("'", " ")).append("', '")
                        .append(movie.getRelease_date().toString()).append("', ")
                        .append(movie.getAdult()).append(", '")
                        .append(movie.getStatus()).append("', ")
                        .append(movie.getRuntime()).append(", ")
                        .append(movie.getPopularity()).append(", '")
                        .append(movie.getPoster_path()).append("')");
            }
            String SQL = SQLBuilder.toString();
            System.out.println(SQL);
            executeSQLStatement(SQL);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addDutchTranslatedMoviesToDb(TranslatedMovie translatedMovie) {
        try {
            openConnection();
            if (connectionIsOpen()) {
                String SQL = "INSERT INTO Translations (Id, Description, Language, Title, URL) ";
                SQL += "VALUES(" + translatedMovie.getId() + ", '" +
                        translatedMovie.getOverview().replace("'", " ") + "', '" +
                        translatedMovie.getLanguage() + "', '" +
                        translatedMovie.getTitle().replace("'", " ") + "', '" +
                        translatedMovie.getPoster_path() + "')";
                executeSQLStatement(SQL);
            }
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

                return rs.next();
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
                            rs.getString("Status"), rs.getDouble("Popularity"), rs.getInt(
                            "Duration"), rs.getString("URL"));
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
