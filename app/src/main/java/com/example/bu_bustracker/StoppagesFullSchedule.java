package com.example.bu_bustracker;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import AppDatabase.StoppageDescription;
import AppDatabase.StoppagesDatabaseHelper;

import static AppDatabase.StoppageContract.FeedEntry.TABLE_NAME;

public class StoppagesFullSchedule extends AppCompatActivity {

    ArrayList<Stoppages> stoppages;
    RecyclerView recyclerView;
    TextView fuck;
    StoppagesAdapter stoppagesAdapter;
    ArrayList<String> arrivalTime;
    HashMap<String,String> stoppageBus;
    private String routenames;
    private String stoppageName;
    LinearLayoutManager linearLayoutManager;
    StoppagesAdapter adapter;
    String query = "SELECT  * FROM " + TABLE_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stoppages_full_schedule);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.stoppages);
        Intent intent = getIntent();
        routenames = intent.getStringExtra("routeName");
        stoppageName = intent.getStringExtra("stoppageName");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Resources res = this.getResources();

        String[] notullbadBusses = res.getStringArray(R.array.notullabad_bus_name);
        String[] barisalClub = res.getStringArray(R.array.barisal_club_bus_name);
        String[] natunBazar = res.getStringArray(R.array.natunbazar_bus_name);

        arrivalTime = new ArrayList<>();
        stoppageBus = new HashMap<String, String>();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        if (getStoppageDetOffline()==null){
            requetRouteBusName();
        }else {
            linearLayoutManager = new LinearLayoutManager(this);
            adapter = new StoppagesAdapter(this,getStoppageDetOffline(),"");
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);
        }


    }

    private void requetRouteBusName(){

        String[] routename = routenames.split(" - ");
        DatabaseReference reference = null;
        if(routename[0].equals("Notullabad") || routename[1].equals("Notullabad")){
            reference = FirebaseDatabase.getInstance().getReference(getString(R.string.notullabad_route_database_ref));
        }
        else if(routename[0].equals("Barisal Club") || routename[1].equals("Barisal Club")){
            reference = FirebaseDatabase.getInstance().getReference(getString(R.string.barisal_club_route_database_ref));

        }
        else if(routename[0].equals("Natun Bazar") || routename[1].equals("Natun Bazar")){
            reference = FirebaseDatabase.getInstance().getReference(getString(R.string.natunbazar_route_database_ref));
        }
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                getBusDet(dataSnapshot);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                getBusDet(dataSnapshot);

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void getBusDet(DataSnapshot dataSnapshot){
        String key = dataSnapshot.getKey();
        Busses value =  dataSnapshot.getValue(Busses.class);

        String timeNotullabad = value.getNotullabad();
        String[] time = getStringss(timeNotullabad);
        for (int i =0;i<time.length;i++){
            String prev = stoppageBus.get(time[i]);
            if (prev==null){
                prev = "";
            }
            stoppageBus.put(time[i],prev+"\n"+value.getBusNames());
            if (arrivalTime.isEmpty()){
                arrivalTime.add(time[i]);
            }
            if (arrivalTime.contains(time[i])==false){
                arrivalTime.add(time[i]);
            }

        }
        linearLayoutManager = new LinearLayoutManager(this);
        adapter = new StoppagesAdapter(this,getStoppageDet(),"");
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
//        String st;
//        st =fuck.getText()+ value.getBusNames()+value.getNotullabad()+value.getUniversity();
//        st = arrivalTime.toString();
//        if (st==null){
//            Toast.makeText(this,"hello",Toast.LENGTH_LONG).show();
//        }
//        getStoppageDet();

    }

    private String[] getStringss(String str){
        String[] namesList = str.split(",");
        return namesList;
    }

    private ArrayList<Stoppages> getStoppageDet(){
        ArrayList<Stoppages> sto = new ArrayList<>();


        Collections.sort(arrivalTime, new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                try {
                    return new SimpleDateFormat("hh:mm a").parse(o1).compareTo(new SimpleDateFormat("hh:mm a").parse(o2));
                } catch (ParseException e) {
                    return 0;
                }
            }
        });
        StoppagesDatabaseHelper db = new StoppagesDatabaseHelper(this);

        for (int i=0;i<arrivalTime.size();i++){
            db.addStoppages(new StoppageDescription(stoppageBus.get(arrivalTime.get(i)),
                    arrivalTime.get(i),routenames,stoppageName));
        }
        ArrayList<StoppageDescription> st = new ArrayList<>();
        st = db.getAllBooks(query);
        db.close();
        for (int i=0;i<arrivalTime.size();i++){

            sto.add(new Stoppages(st.get(i).getBusName(),st.get(i).getArrivalTime(),
                    routenames,st.get(i).getStoppage()));
        }
        return sto;
    }

    private ArrayList<Stoppages> getStoppageDetOffline(){
        ArrayList<Stoppages> sto = new ArrayList<>();
        StoppagesDatabaseHelper db = new StoppagesDatabaseHelper(this);
        if (db.getAllBooks(query).size()==0){
            return null;
        }

        ArrayList<StoppageDescription> st = new ArrayList<>();
        st = db.getAllBooks(query);
        for (int i=0;i<db.getAllBooks(query).size();i++){

            sto.add(new Stoppages(st.get(i).getBusName(),st.get(i).getArrivalTime(),
                    routenames,st.get(i).getStoppage()));
        }
        db.close();
        return sto;
    }

}
