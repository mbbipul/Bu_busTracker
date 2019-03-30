package com.example.bu_bustracker;

public class Stoppages {

    public String busNames;
    public String arrivalTime;
    public String routeName;
    public String  stoppage;


    public String getBusNames() {
        return busNames;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public String getRouteName() {
        String[] routename = routeName.split(" - ");
        if(routename[0].equals("Notullabad") || routename[1].equals("Notullabad")){
            return "Notullabad";
        }
        else if(routename[0].equals("Barisal Club") || routename[1].equals("Barisal Club")){
            return "Barisal Club";
        }
        else if(routename[0].equals("Natun Bazar") || routename[1].equals("Natun Bazar")){
            return "Natun Bazar";
        }
        return routeName;
    }

    public String getStoppage(){
        return stoppage;
    }
    public Stoppages(String mBusName, String mArrivalTime, String mRouteName, String mStoppage){
        this.busNames = mBusName;
        this.arrivalTime = mArrivalTime;
        this.routeName = mRouteName;
        this.stoppage = mStoppage;
    }
}
