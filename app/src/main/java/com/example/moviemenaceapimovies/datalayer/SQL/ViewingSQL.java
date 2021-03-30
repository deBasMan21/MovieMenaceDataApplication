package com.example.moviemenaceapimovies.datalayer.SQL;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.moviemenaceapimovies.domain.Viewing;

import java.util.ArrayList;

public class ViewingSQL extends DatabaseConnection {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addViewingsToDB(ArrayList<Viewing> viewings){
        openConnection();
        try{
            String SQL = "INSERT INTO Viewing (Date, StartTime, Price, [3D], MovieID, RoomNumber) VALUES ";
            for (Viewing view: viewings) {
                if(SQL.length() > 80){
                    SQL = SQL + ", ";
                }
                SQL = SQL + "('" + view.getDateFormatted() + "', '" + view.getTimeFormatted() + "', " + view.getPrice() + ", " + (view.isThreeDimensional() ? 1 : 0) + ", " + view.getMovieID() + ", " + view.getRoomNumber() + ")";
            }
            System.out.println(SQL);
            executeSQLStatement(SQL);
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }
}
