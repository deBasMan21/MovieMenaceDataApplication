package com.example.moviemenaceapimovies.logic;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.moviemenaceapimovies.domain.Viewing;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class ViewingManager {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<Viewing> createViewings(LocalDate date){
        ArrayList<Viewing> viewings = new ArrayList<>();
        for(int i = 1; i < 5; i++){
            for(int j = 12; j < 20; j += 4){
                LocalTime lt = LocalTime.parse(j + ":00");
                LocalDateTime dt = LocalDateTime.of(date, lt);
            }
        }
        return viewings;
    }
}
