package BusFinderModule;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.TextView;
import android.widget.Toast;

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

import static BusFinderModule.BusHelper.disTanceFromOther;
import static BusFinderModule.BusHelper.disTanceFromUniversity;
import static BusFinderModule.BusHelper.getMyJourneyDirection;

public class GetTheBus {
    String origin;
    String destination;
    private HashMap<String,String> allBusLocation1;
    private HashMap<String,String> allBusLocation2;
    private boolean isMyJourneyDirection;
    private boolean[] isMyJorneyRoutes;
    Context context;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public GetTheBus(Context mContext,String mOrigin, String mDestination,String originName,String destinationName){
        this.context = mContext;
        this.origin = mOrigin;
        this.destination = mDestination;
        this.isMyJourneyDirection = getMyJourneyDirection(mOrigin,mDestination);
        this.isMyJorneyRoutes = BusHelper.myJourneyRoutes(mContext,originName,destinationName);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private  HashMap<String,Boolean> getAllBusDirection(){
        HashMap<String,Boolean> allBusDirection;

        allBusLocation1 = new HashMap<String, String>();
        allBusLocation2 = new HashMap<String, String>();
        allBusDirection = new HashMap<String, Boolean>();

        Set keys = allBusLocation1.keySet();
        for (Iterator i = keys.iterator(); i.hasNext();){
            String key = (String) i.next();
            double distanceLoc1 = disTanceFromUniversity(allBusLocation1.get(key));
            double distanceLoc2 = disTanceFromUniversity(allBusLocation2.get(key));
            if (distanceLoc1<distanceLoc2){
                allBusDirection.put(key,false);
            }
            else if(distanceLoc2<=distanceLoc1){
                allBusDirection.put(key,true);
            }
        }
        return allBusDirection;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<String> getAvailableBusses(){
        HashMap<String,Boolean> allBusDir = getAllBusDirection();
        ArrayList<String> availablebus = new ArrayList<>();
        Resources res = context.getResources();
        String[] busNameNotullabad = res.getStringArray(R.array.notullabad_bus_name);
        String[] busNameBarisalClub = res.getStringArray(R.array.barisal_club_bus_name);
        String[] busNameNatunBazar = res.getStringArray(R.array.natunbazar_bus_name);

        HashMap<String ,Boolean> natullabadBus = new HashMap<String, Boolean>();
        HashMap<String ,Boolean> natunBazarBus = new HashMap<String, Boolean>();
        HashMap<String ,Boolean> barisalClubBus = new HashMap<String, Boolean>();


        for (int i =0;i<busNameNotullabad.length;i++){
            natullabadBus.put(busNameNotullabad[i],true);
        }
        for (int i =0;i<busNameNatunBazar.length;i++){
            natunBazarBus.put(busNameNatunBazar[i],true);
        }
        for (int i =0;i<busNameBarisalClub.length;i++){
            barisalClubBus.put(busNameBarisalClub[i],true);
        }

        Set keys = allBusDir.keySet();
        for (Iterator i = keys.iterator(); i.hasNext();){
            String key = (String) i.next();
            if (isMyJourneyDirection){
                if (isMyJourneyDirection==allBusDir.get(key)){
                    if (isMyJorneyRoutes[0] == natullabadBus.get(key) ||
                            isMyJorneyRoutes[1] == natullabadBus.get(key) ||
                            isMyJorneyRoutes[2] == natullabadBus.get(key) ){
                        if (getAllBusDistanceFromOrgin(allBusLocation1,true).get(key)){
                            availablebus.add(key);
                        }
                    }
                }

            }
            else {
                if (isMyJourneyDirection==allBusDir.get(key)){
                    if (isMyJorneyRoutes[0] == natullabadBus.get(key) ||
                            isMyJorneyRoutes[1] == natullabadBus.get(key) ||
                            isMyJorneyRoutes[2] == natullabadBus.get(key) ){
                        if (getAllBusDistanceFromOrgin(allBusLocation1,false).get(key)){
                            availablebus.add(key);
                        }
                    }
                }

            }
        }
        return availablebus;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private HashMap<String,Boolean> getAllBusDistanceFromOrgin(HashMap<String,String> allBusLoc,boolean dir){
        HashMap<String,Boolean> allBusDis = new HashMap<String, Boolean>();
        float originDis = (float) disTanceFromUniversity(origin);
        Set keys = allBusLoc.keySet();
        for (Iterator i = keys.iterator(); i.hasNext();){
            String key = (String) i.next();
            if (dir){
                if (originDis>=disTanceFromUniversity(allBusLoc.get(key))){
                    allBusDis.put(key,true);
                }
                else {
                    allBusDis.put(key,false);
                }
            }
            else {
                if (originDis>=disTanceFromOther(allBusLoc.get(key))){
                    allBusDis.put(key,true);
                }
                else {
                    allBusDis.put(key,false);
                }
            }

        }
        return allBusDis;
    }
    private void subscribeToUpdatesBusLocation() {
        final DataSnapshot[] mDataSnapshot = new DataSnapshot[1];
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("locations");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                String key = dataSnapshot.getKey();
                HashMap<String, Object> value = (HashMap<String, Object>) dataSnapshot.getValue();
                double lat = Double.parseDouble(value.get("latitude").toString());
                double lng = Double.parseDouble(value.get("longitude").toString());
                allBusLocation1.put(key,lat+","+lng);
                Toast.makeText(context,allBusLocation1.get(key),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                String key = dataSnapshot.getKey();
                HashMap<String, Object> value = (HashMap<String, Object>) dataSnapshot.getValue();
                double lat = Double.parseDouble(value.get("latitude").toString());
                double lng = Double.parseDouble(value.get("longitude").toString());
                allBusLocation1.put(key,lat+","+lng);
                Toast.makeText(context,allBusLocation1.get(key),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        Toast.makeText(context,allBusLocation1.get("Paira"),Toast.LENGTH_LONG).show();
    }
    private  void subscribeToUpdatesBusLocation2() {
        final DataSnapshot[] mDataSnapshot = new DataSnapshot[1];
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("locations");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                String key = dataSnapshot.getKey();
                HashMap<String, Object> value = (HashMap<String, Object>) dataSnapshot.getValue();
                double lat = Double.parseDouble(value.get("latitude").toString());
                double lng = Double.parseDouble(value.get("longitude").toString());
                allBusLocation2.put(key,lat+","+lng);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                String key = dataSnapshot.getKey();
                HashMap<String, Object> value = (HashMap<String, Object>) dataSnapshot.getValue();
                double lat = Double.parseDouble(value.get("latitude").toString());
                double lng = Double.parseDouble(value.get("longitude").toString());
                allBusLocation2.put(key,lat+","+lng);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        Toast.makeText(context,allBusLocation1.get("Paira"),Toast.LENGTH_LONG).show();

    }


}

