package com.example.bu_bustracker;

public class Route {
    private String departurePlace;
    private String destinitionPlace;
    private int iconSrc;

    public Route(String mDeparturePlace,String mDestinitionPlace,int mIconSrc){
        this.departurePlace = mDeparturePlace;
        this.destinitionPlace = mDestinitionPlace;
        this.iconSrc = mIconSrc;
    }

    public String getDeparturePlace(){
        return this.departurePlace;
    }

    public String getDestinitionPlace(){
        return this.destinitionPlace;
    }

    public int getIconSrc(){
        return this.iconSrc;
    }

}
