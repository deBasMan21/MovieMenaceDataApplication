package com.example.moviemenaceapimovies.domain;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Viewing {
    private int id;
    private LocalDateTime date;
    private double price;
    private boolean threeDimensional;
    private int roomNumber;
    private int movieID;

    public Viewing(int id, LocalDateTime date, double price, boolean threeDimensional, int room, int movieID) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.threeDimensional = threeDimensional;
        this.roomNumber = room;
        this.movieID = movieID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getDateFormatted(){
        LocalDate date = LocalDate.of(this.date.getYear(), this.date.getMonth(),  this.date.getDayOfMonth());
        return date.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getTimeFormatted(){
        LocalTime time = LocalTime.of(this.date.getHour(), this.date.getMinute());
        return time.toString();
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isThreeDimensional() {
        return threeDimensional;
    }

    public void setThreeDimensional(boolean threeDimensional) {
        this.threeDimensional = threeDimensional;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }
}
