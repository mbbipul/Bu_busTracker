package Location;

import java.util.HashMap;
import java.util.Map;

public class BusStopages {
    Map<String, String > busStopages = new HashMap<String, String>();

    public BusStopages(){
    }

    public void setBusStopages(String stopage,String latLng ){
        this.busStopages.put(latLng,latLng);
    }

    public String getBusStopages() {
        return busStopages.get("bb");
    }
}
