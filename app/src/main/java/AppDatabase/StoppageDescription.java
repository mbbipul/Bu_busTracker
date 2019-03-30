package AppDatabase;

import android.widget.Toast;

/**
 * Created by Belal on 9/30/2017.
 */

public class StoppageDescription {
    int id;
    String busName, arrivalTime, routeName, stoppage;

    public StoppageDescription( String name, String arrivalTime, String routeName, String stoppage) {
        this.busName = name;
        this.arrivalTime = arrivalTime;
        this.routeName = routeName;
        this.stoppage = stoppage;
    }

    public StoppageDescription(){}
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
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

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getStoppage() {
        return stoppage;
    }

    public void setStoppage(String stoppage) {
        this.stoppage = stoppage;
    }
}
