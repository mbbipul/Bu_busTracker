package com.example.bu_bustracker;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Interpolator;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;


import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

import BusFinderModule.BusHelper;
import DirectionModule.DirectionFinder;
import DirectionModule.DirectionFinderListener;
import DirectionModule.Route;

import static BusFinderModule.BusHelper.disTanceFromOther;
import static BusFinderModule.BusHelper.disTanceFromUniversity;

public class FindALlBus extends FragmentActivity implements
        DirectionFinderListener,
        OnMapReadyCallback {


    private static final String TAG = FindBus.class.getSimpleName();
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private HashMap<String, Marker> mMarkers = new HashMap<>();
    private GoogleMap mMap;
    private boolean mPermissionDenied = false;

    private List<Circle> originMarkers = new ArrayList<Circle>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    HashMap<String, String> busStoppagesHasMap;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_all_bus);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding All Busses..!", true);



    }

    private void sendRequest(String mCurrentLocation) {
        String latLngOrigin;
        latLngOrigin = mCurrentLocation.toString();
        showBus(latLngOrigin);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Authenticate with Firebase when the Google map is loaded
        mMap = googleMap;
        mMap.setMaxZoomPreference(16);
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        loginToFirebase();
    }



    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */

    private void loginToFirebase() {
        String email = getString(R.string.firebase_email);
        String password = getString(R.string.firebase_password);
        // Authenticate with Firebase and subscribe to updates
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
                email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    requestLocationUpdates();
                } else {
                    Log.v(TAG, "firebase auth failed");
                }
            }
        });
    }


    @Override
    public void onDirectionFinderStart() {
//        progressDialog = ProgressDialog.show(this, "Please wait.",
//                "Finding direction..!", true);
//
//        if (originMarkers != null) {
//            for (Circle marker : originMarkers) {
//                marker.remove();
//            }
//        }
//
//        if (destinationMarkers != null) {
//            for (Marker marker : destinationMarkers) {
//                marker.remove();
//            }
//        }
//
//        if (polylinePaths != null) {
//            for (Polyline polyline : polylinePaths) {
//                polyline.remove();
//            }
//        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<Circle>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));

//            originMarkers.add(mMap.addMarker(new MarkerOptions()
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
//                    .title(route.startAddress)
//                    .position(route.startLocation)));
            originMarkers.add(mMap.addCircle(new CircleOptions()
                    .center(route.startLocation)
                    .radius(50)
                    .strokeColor(Color.BLUE)
                    .fillColor(Color.GREEN)));
//            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
//                    .title(route.endAddress)
//                    .position(route.endLocation)));
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(color).
                    width(15);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
//        HashMap<String,ArrayList> nearBus = new HashMap<String, ArrayList>();
//        Set keys = mMarkers.keySet();
//        for (Iterator i = keys.iterator(); i.hasNext();){
//            String key = (String) i.next();
//            LatLng l1 = mMarkers.get(key).getPosition();
//            for (Iterator j = keys.iterator(); i.hasNext();){
//                ArrayList<String> near = new ArrayList<String>();
//                String key2 = (String) i.next();
//                LatLng l2 = mMarkers.get(key2).getPosition();
//                if (getDistance(l1.latitude,l1.longitude,l2.latitude,l2.longitude)<=100){
//
//                }
//            }
//
//        }

    }

    private void requestLocationUpdates() {
        LocationRequest request = new LocationRequest();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            // Request location updates and when an update is
            // received, store the location in Firebase
            client.requestLocationUpdates(request, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        Log.d(TAG, "location update " + location);
                        //  Toast.makeText(TrackerService.this, location.toString(), Toast.LENGTH_SHORT).show();
                        String gb = String.valueOf(location.getLatitude()+","+location.getLongitude());
                        sendRequest(gb);
                    }
                }
            }, null);
        }
    }

    private float getDistance(double startLatitude,double startLongitude,
                              double endLatitude,double endLongitude){
        float results = (float) distance(startLatitude,startLongitude,endLatitude,endLongitude);
        return results;
    }
    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    private void showBus(String currentLocation){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(getString(R.string.firebase_path));
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                showDirection(dataSnapshot,currentLocation);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                showDirection(dataSnapshot,currentLocation);

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.v(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void showDirection(DataSnapshot dataSnapshot,String currentLocation){
        // boundaries required to show them all on the map at once
        String key = dataSnapshot.getKey();
        HashMap<String, Object> value = (HashMap<String, Object>) dataSnapshot.getValue();
        double lat = Double.parseDouble(value.get("latitude").toString());
        double lng = Double.parseDouble(value.get("longitude").toString());
        float bearing = Float.parseFloat(value.get("bearing").toString());
        LatLng location = new LatLng(lat, lng);
        if (!mMarkers.containsKey(key)) {

            int height = 100;
            int width = 100;
//            BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.ic_navigation_black_24dp);
//            Bitmap b=bitmapdraw.getBitmap();
            Bitmap b=BusHelper.getBitmapFromVectorDrawable(this,R.drawable.ic_navigation_black_24dp);
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
            Marker marker = mMap.addMarker(new MarkerOptions().title(key)
                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                    .position(location)
                    .rotation(bearing));
                mMarkers.put(key, marker);
                String busLoc = location.latitude+","+location.longitude;
                try {
                    new DirectionFinder(this,currentLocation,busLoc).execute();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

        } else {
                mMarkers.get(key).setPosition(location);
                String busLoc = location.latitude+","+location.longitude;
                try {
                    new DirectionFinder(this,currentLocation,busLoc).execute();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
        }
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : mMarkers.values()) {
            builder.include(marker.getPosition());
        }
    }
}