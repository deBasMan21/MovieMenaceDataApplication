package com.example.moviemenaceapimovies.datalayer.SQL;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.moviemenaceapimovies.domain.Viewing;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class ViewingSQL extends DatabaseConnection {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addViewingsToDB(ArrayList<Viewing> viewings){
        openConnection();
        try{
            StringBuilder SQL = new StringBuilder("INSERT INTO Viewing (Date, StartTime, Price, " +
                    "[3D], MovieID, RoomNumber) VALUES ");
            for (Viewing view: viewings) {
                if(SQL.length() > 80){
                    SQL.append(", ");
                }
                SQL.append("('").append(view.getDateFormatted()).append("', '")
                        .append(view.getTimeFormatted()).append("', ")
                        .append(view.getPrice()).append(", ")
                        .append(view.isThreeDimensional() ? 1 : 0).append(", ")
                        .append(view.getMovieID()).append(", ")
                        .append(view.getRoomNumber()).append(")");
            }
            System.out.println(SQL);
            executeSQLStatement(SQL.toString());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean areViewingsCreated(){
        openConnection();
        try{
            String SQL = "SELECT * FROM Viewing WHERE Date > GETDATE()";
            executeSQLSelectStatement(SQL);
            return rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
    }

    public ArrayList<Viewing> getViewingsByMovieId(int id) {
        ArrayList<Viewing> viewings = null;
        openConnection();
        try {
            if (connectionIsOpen()) {
                viewings = new ArrayList<>();
                String SQL = "SELECT * FROM Viewing WHERE MovieID IN(" + id + ")";

                rs = executeSQLSelectStatement(SQL);

                while (rs.next()) {
                    LocalDateTime dateAndTime = LocalDateTime.of(LocalDate.parse(rs.getString("Date")),
                            LocalTime.parse(rs.getString("StartTime")));
                    Viewing viewing = new Viewing(rs.getInt("ViewID"), dateAndTime, rs.getDouble(
                            "Price"), rs.getBoolean("3D"), rs.getInt("RoomNumber"), rs.getInt(
                            "MovieID"));
                    viewings.add(viewing);
                }
                return viewings;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return viewings;
    }

    public void removeViewingsFromDb() {
        openConnection();
        try {
            if (connectionIsOpen()) {
                String SQL = "DELETE FROM Viewing";

                executeSQLStatement(SQL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
