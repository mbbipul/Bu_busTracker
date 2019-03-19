package BusFinderModule;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;

import com.example.bu_bustracker.R;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.invoke.MethodType;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class BusHelper {

    static HashMap<String,String> allBusLocation;
    static HashMap<String,String> allBusLocation2;
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean getMyJourneyDirection(String busStoppagesOrgin,String busStoppagesDestination){
        boolean dirRes = true;
        double busStoOriginDis = disTanceFromUniversity(busStoppagesOrgin);
        double busStoDesDis = disTanceFromUniversity(busStoppagesDestination);
        if (busStoOriginDis>busStoDesDis)
            dirRes = false;
        else if (busStoOriginDis<=busStoDesDis)
            dirRes = true;
        return dirRes;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static double disTanceFromUniversity(String origin){

        double latStoppageOrigin = DistanceTwo.getDouble(origin)[0];
        double lngStoppageOrigin = DistanceTwo.getDouble(origin)[1];
        double latUniversity = 22.659550;
        double lngUniversity = 90.361506;
        return DistanceTwo.distance(latStoppageOrigin,lngStoppageOrigin,latUniversity,lngUniversity);

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static double disTanceFromOther(String origin){

        double latStoppageOrigin = DistanceTwo.getDouble(origin)[0];
        double lngStoppageOrigin = DistanceTwo.getDouble(origin)[1];
        double latUniversity = 22.7252164;
        double lngUniversity = 90.3858782;
        return DistanceTwo.distance(latStoppageOrigin,lngStoppageOrigin,latUniversity,lngUniversity);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static HashMap<String,Boolean> getAllBusDirection(){
        allBusLocation = new HashMap<String, String>();
        allBusLocation2 = new HashMap<String, String>();
        subscribeToUpdatesBusLocation();
        subscribeToUpdatesBusLocation2();
        HashMap<String,Boolean> allBusDir = new HashMap<String, Boolean>();
        subscribeToUpdatesBusLocation();
        subscribeToUpdatesBusLocation2();

        Set keys = allBusLocation.keySet();
        for (Iterator i = keys.iterator(); i.hasNext();){
            String key = (String) i.next();
            double distanceLoc1 = disTanceFromUniversity(allBusLocation.get(key));
            double distanceLoc2 = disTanceFromUniversity(allBusLocation2.get(key));
            if (distanceLoc1<distanceLoc2){
                allBusDir.put(key,false);
            }
            else if(distanceLoc2<=distanceLoc1){
                allBusDir.put(key,true);
            }
        }


//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                subscribeToUpdatesBusLocation2();
//            }
//        }, 600);
        return allBusDir;
    }

    private static void subscribeToUpdatesBusLocation() {
        final DataSnapshot[] mDataSnapshot = new DataSnapshot[1];
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("locations");
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                String key = dataSnapshot.getKey();
                HashMap<String, Object> value = (HashMap<String, Object>) dataSnapshot.getValue();
                double lat = Double.parseDouble(value.get("latitude").toString());
                double lng = Double.parseDouble(value.get("longitude").toString());
                allBusLocation.put(key,lat+","+lng);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                String key = dataSnapshot.getKey();
                HashMap<String, Object> value = (HashMap<String, Object>) dataSnapshot.getValue();
                double lat = Double.parseDouble(value.get("latitude").toString());
                double lng = Double.parseDouble(value.get("longitude").toString());
                allBusLocation.put(key,lat+","+lng);
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
    }
    private static void subscribeToUpdatesBusLocation2() {
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
                allBusLocation.put(key,lat+","+lng);
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
    }

    public static boolean[] myJourneyRoutes(Context context,String busStoppagesOrgin,String busStoppagesDestination){
        boolean[] array = new boolean[3];

        Resources res = context.getResources();
        String[] busStopagesNotullabad = res.getStringArray(R.array.university_to_notullbad);
        String[] busStopagesBarisalClub = res.getStringArray(R.array.university_to_barisal_club);
        String[] busStopagesNatunBazar = res.getStringArray(R.array.university_to_notun_bazar);
        for (int i = 0;i<busStopagesNotullabad.length;i++){
            if (busStopagesNotullabad[i].equals(busStoppagesOrgin) ||
                    busStopagesNotullabad[i].equals(busStoppagesDestination)){
                array[0] = true;
            }

        }for (int i = 0;i<busStopagesBarisalClub.length;i++){
            if (busStopagesBarisalClub[i].equals(busStoppagesOrgin) ||
                    busStopagesBarisalClub[i].equals(busStoppagesDestination)){
                array[1] = true;
            }

        }for (int i = 0;i<busStopagesNatunBazar.length;i++){
            if (busStopagesNatunBazar[i].equals(busStoppagesOrgin) ||
                    busStopagesNatunBazar[i].equals(busStoppagesDestination)){
                array[2] = true;
            }

        }

        return array;
    }

//    private HashMap<String,String> getAllBusRoute(){
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Routes/UniversityToBarisalClub/Bus_Name");
//        ref.addChildEventListener(new ChildEventListener() {
//
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        }
//    }



    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }


}
