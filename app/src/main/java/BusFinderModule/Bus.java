package BusFinderModule;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import com.example.bu_bustracker.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Stream;

import DirectionModule.Distance;
import DirectionModule.Duration;

public class Bus {

    private String busName;
    private boolean busDirection; // if 'true' bus direction to varsity and if 'false' then from varsity
    private double distance;
    private Duration duration;
    private String busLatLng;
    private HashMap<String,String> busDepartureTime;


    private String busStoppagesOrgin;
    private String busStoppagesDestination;
    private String busLocation;

    Context context;

    public Bus(Context context,String mBusStoppagesOrgin, String mBusStoppagesDestination, String busName){
        this.busStoppagesOrgin = mBusStoppagesOrgin;
        this.busStoppagesDestination = mBusStoppagesDestination;
        this.busName = busName;
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private boolean isBusDirection(){
        return BusHelper.getAllBusDirection().get(busName);
    }

//    private String getRouteName(){
//
//    }
//
//
//    private HashMap<String,String>,HashMap<String,String> getBusDepartureTime(){
//
//    }


}
