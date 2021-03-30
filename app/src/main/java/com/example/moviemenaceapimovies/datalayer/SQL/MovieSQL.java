package com.example.moviemenaceapimovies.datalayer.SQL;

import com.example.moviemenaceapimovies.domain.Movie;

import java.util.ArrayList;

public class MovieSQL extends DatabaseConnection{

        public void addMoviesToDb(ArrayList<Movie> movies){
            try{
                openConnection();
                String SQL = "INSERT INTO Movie (Id, Title, Description, ReleaseDate, Adult, Status, Duration, Popularity) VALUES";
                for (Movie movie : movies) {
                    if(SQL.length() > 110){
                        SQL = SQL + ",";
                    }
                    SQL = SQL + "(" + movie.getId() + ", '" + movie.getTitle().replace("'", " ") + "', '" + movie.getOverview().replace("'", " ") + "', '" + movie.getRelease_date().toString() + "', " + movie.getAdult() + ", '" + movie.getStatus() + "', " + movie.getRuntime() + ", " + movie.getPopularity() + ")";
                }
                System.out.println(SQL);
                executeSQLStatement(SQL);

            } catch (Exception e){

            }
        }
}
