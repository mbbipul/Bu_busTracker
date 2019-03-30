package com.example.bu_bustracker;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.Resource;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.List;

public class RouteLineDetails extends AppCompatActivity implements OnMapReadyCallback {

        private CardView stickyView;
        private RecyclerView recyclerView;
        private View heroImageView;
        private ImageButton maphideshow;
        private TextView textHeader;
        private static int y;

        private View stickyViewSpacer;
        LinearLayoutManager linearLayoutManager;
        private int previousExpandedPosition = -1;
        private String routeNames;
    private int MAX_ROWS = 20;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.route_line_para_main);
            Intent intent = getIntent();
            routeNames = intent.getStringExtra("routeName");

            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map_route_item);
            mapFragment.getMapAsync(this);
            mapFragment.getView().setVisibility(View.GONE);

            /* Initialise list view, hero image, and sticky view */
          //  routeItemsListView = (RecyclerView) findViewById(R.id.recyclerView);

          maphideshow = findViewById(R.id.maphideshow);

          LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          View listHeader = inflater.inflate(R.layout.list_header, null);
          stickyViewSpacer = listHeader.findViewById(R.id.stickyViewPlaceholder);

          textHeader = findViewById(R.id.txtHeader);
          textHeader.setTypeface(textHeader.getTypeface(), Typeface.BOLD);
          textHeader.setText(routeNames);

            // Initializing list view with the custom adapter

            ArrayList <RouteLineBusStoppagesDetails> itemList = new ArrayList<RouteLineBusStoppagesDetails>();

            recyclerView = (RecyclerView)findViewById(R.id.listView);
            linearLayoutManager = new LinearLayoutManager(this);
            RouteLineStoppageAdapter adapter = new RouteLineStoppageAdapter(this,getListItems(routeNames),routeNames);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(adapter);

            maphideshow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showHideFragment(mapFragment);
                }
            });

                    // Populating list items
            for(int i=0; i<100; i++) {
                itemList.add(new RouteLineBusStoppagesDetails("Item " + i));
            }
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    // super.onScrolled(recyclerView, dx, dy);
                    y=dy;
                }
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if(recyclerView.SCROLL_STATE_DRAGGING==newState){
                        //fragProductLl.setVisibility(View.GONE);
                    }
                    if(recyclerView.SCROLL_STATE_IDLE==newState){
                        // fragProductLl.setVisibility(View.VISIBLE);
//                        if(y<=0){
//                            fragProductLl.setVisibility(View.VISIBLE);
//                        }
//                        else{
//                            y=0;
//                            fragProductLl.setVisibility(View.GONE);
//                        }
                    }

                }
            });

        }
    public ArrayList<RouteLineBusStoppagesDetails> getListItems(String routeNames)
    {
        String[] routename = getString(routeNames);
        Resources res = this.getResources();
        String[] stoppages = null ;
        ArrayList<RouteLineBusStoppagesDetails> routeLineBusStoppagesDetails = new ArrayList<>();


        if(routename[0].equals("Notullabad") || routename[1].equals("Notullabad")){
            stoppages = res.getStringArray(R.array.university_to_notullbad);
            if (routename[0].equals("University")){
                for (int i=stoppages.length-1;i>=0;i--){
                    routeLineBusStoppagesDetails.add(new RouteLineBusStoppagesDetails(stoppages[i]));
                }
            }else{
                for (int i=0;i<stoppages.length;i++){
                    routeLineBusStoppagesDetails.add(new RouteLineBusStoppagesDetails(stoppages[i]));
                }
            }
        }
        else if(routename[0].equals("Barisal Club") || routename[1].equals("Barisal Club")){
            stoppages = res.getStringArray(R.array.university_to_barisal_club);
            if (routename[0].equals("University")){
                for (int i=stoppages.length-1;i>=0;i--){
                    routeLineBusStoppagesDetails.add(new RouteLineBusStoppagesDetails(stoppages[i]));
                }
            }else{
                for (int i=0;i<stoppages.length;i++){
                    routeLineBusStoppagesDetails.add(new RouteLineBusStoppagesDetails(stoppages[i]));
                }
            }
        }
        else if(routename[0].equals("Natun Bazar") || routename[1].equals("Natun Bazar")){
            stoppages = res.getStringArray(R.array.university_to_notun_bazar);
            if (routename[0].equals("University")){
                for (int i=stoppages.length-1;i>=0;i--){
                    routeLineBusStoppagesDetails.add(new RouteLineBusStoppagesDetails(stoppages[i]));
                }
            }else{
                for (int i=0;i<stoppages.length;i++){
                    routeLineBusStoppagesDetails.add(new RouteLineBusStoppagesDetails(stoppages[i]));
                }
            }
        }

        return routeLineBusStoppagesDetails;
    }
    private String[] getString(String str){
        String[] namesList = str.split(" - ");
        return namesList;
    }
    public void showHideFragment(final SupportMapFragment mapFragment){
            if (mapFragment.isVisible()){
                TranslateAnimation translateAnimation = new TranslateAnimation(0, 50, 0, 0);
                int animationTime = 200;
                translateAnimation.setDuration(animationTime);
                translateAnimation.setFillEnabled(true);
                translateAnimation.setFillAfter(true);
                mapFragment.getView().startAnimation(translateAnimation);
                mapFragment.getView().setVisibility(View.GONE);            }
            else {
                TranslateAnimation translateAnimation = new TranslateAnimation(0, 50, 0, 0);
                int animationTime = 200;
                translateAnimation.setDuration(animationTime);
                translateAnimation.setFillEnabled(true);
                translateAnimation.setFillAfter(true);
                mapFragment.getView().startAnimation(translateAnimation);
                mapFragment.getView().setVisibility(View.VISIBLE);              }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

}
