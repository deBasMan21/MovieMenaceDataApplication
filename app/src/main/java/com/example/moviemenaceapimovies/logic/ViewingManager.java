package com.example.moviemenaceapimovies.logic;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.moviemenaceapimovies.domain.Movie;
import com.example.moviemenaceapimovies.domain.Viewing;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class ViewingManager {
    private boolean threeDimensional = true;
    private double price = 12.50;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Viewing> createViewings(LocalDate date, ArrayList<Movie> movies){
        ArrayList<Viewing> viewings = new ArrayList<>();
        for(int i = 1; i < 5; i++){
            for(int j = 12; j < 24; j += 4){
                LocalTime lt = LocalTime.parse(j + ":00");
                LocalDateTime dt = LocalDateTime.of(date, lt);
                viewings.add(new Viewing(0, dt, price, is3D(), i, movies.get(getRandomNumber()).getId()));
            }
        }
        return viewings;
    }

    public int getRandomNumber(){
        double randomNumber = Math.random() * 60;
        int randomNumberParsed = (int) randomNumber;
        return randomNumberParsed;
    }

    public boolean is3D(){
        if(threeDimensional){
            threeDimensional = false;
            price = 15.00;
        } else{
            threeDimensional = true;
            price = 12.50;
        }
        return threeDimensional;
    }
}
