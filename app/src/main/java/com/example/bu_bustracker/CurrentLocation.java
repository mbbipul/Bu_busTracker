package com.example.bu_bustracker;

public class CurrentLocation {
    private double lattitiude;
    private double longititude;
    public CurrentLocation(double lat,double lng){
        this.lattitiude = lat;
        this.lattitiude = lng;
    }

    public double getLattitiude() {
        return lattitiude;
    }

    public double getLongititude() {
        return longititude;
    }
}
