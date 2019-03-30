package com.example.bu_bustracker;

public class RouteLineBusStoppagesDetails {
    private String name;
    int id;

    public RouteLineBusStoppagesDetails(String n) {
        name = n;
    }

    public RouteLineBusStoppagesDetails() {

    }

    public String getName() {
        return name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
