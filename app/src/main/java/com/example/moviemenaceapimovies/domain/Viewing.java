package com.example.moviemenaceapimovies.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Viewing {
    private int id;
    private LocalDateTime date;
    private double price;
    private boolean threeDimensional;
    private Room room;
    private Map<Integer, Ticket> tickets;

    public Viewing(int id, LocalDateTime date, double price, boolean threeDimensional, Room room) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.threeDimensional = threeDimensional;
        this.room = room;
        this.tickets = new HashMap<>();
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Map<Integer, Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Map<Integer, Ticket> tickets) {
        this.tickets = tickets;
    }
}
