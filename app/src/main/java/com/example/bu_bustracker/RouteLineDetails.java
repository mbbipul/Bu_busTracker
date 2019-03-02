package com.example.bu_bustracker;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RouteLineDetails extends AppCompatActivity {

        private TextView stickyView;
        private ListView routeItemsListView;
        private View heroImageView;

        private View stickyViewSpacer;

        private int MAX_ROWS = 20;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.route_line_para_main);

            /* Initialise list view, hero image, and sticky view */
            routeItemsListView = (ListView) findViewById(R.id.listView);

            heroImageView = findViewById(R.id.heroImageView);
            stickyView = (TextView) findViewById(R.id.stickyView);

            /* Inflate list header layout */
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View listHeader = inflater.inflate(R.layout.list_header, null);
            stickyViewSpacer = listHeader.findViewById(R.id.stickyViewPlaceholder);

            /* Add list view header */
            routeItemsListView.addHeaderView(listHeader);

            /* Handle list View scroll events */
            routeItemsListView.setOnScrollListener(new AbsListView.OnScrollListener() {

                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                    /* Check if the first item is already reached to top.*/
                    if (routeItemsListView.getFirstVisiblePosition() == 0) {
                        View firstChild = routeItemsListView.getChildAt(0);
                        int topY = 0;
                        if (firstChild != null) {
                            topY = firstChild.getTop();
                        }

                        int heroTopY = stickyViewSpacer.getTop();
                        stickyView.setY(Math.max(0, heroTopY + topY));

                        /* Set the image to scroll half of the amount that of ListView */
                        heroImageView.setY(topY * 0.5f);
                    }
                }
            });


            RouteLine routeLine1 = new RouteLine("Amtola mor");
            RouteLine routeLine2 = new RouteLine("Banglabazar mor");
            RouteLine routeLine3 = new RouteLine("Barisal Club");
            RouteLine routeLine4 = new RouteLine("Kathal Tola");
            RouteLine routeLine5 = new RouteLine("Nuriya Scholl");
            RouteLine routeLine6 = new RouteLine("Rupatoli Housing");
            RouteLine routeLine7 = new RouteLine("Toll Ghor");
            RouteLine routeLine8 = new RouteLine("University");


            ArrayList<RouteLine> routeLines = new ArrayList<>(); // calls function to get items list

            routeLines.add(routeLine1);
            routeLines.add(routeLine2);
            routeLines.add(routeLine3);
            routeLines.add(routeLine4);
            routeLines.add(routeLine5);
            routeLines.add(routeLine6);
            routeLines.add(routeLine7);
            routeLines.add(routeLine8);

            routeLines.add(routeLine6);
            routeLines.add(routeLine7);
            routeLines.add(routeLine8);
            routeLines.add(routeLine6);
            routeLines.add(routeLine7);
            routeLines.add(routeLine8);
            routeLines.add(routeLine6);
            routeLines.add(routeLine7);
            routeLines.add(routeLine8);
            routeLines.add(routeLine6);
            routeLines.add(routeLine7);
            routeLines.add(routeLine6);
            routeLines.add(routeLine7);
            routeLines.add(routeLine8);
            routeLines.add(routeLine8);

            RouteLineDetAdapter routeLineAdapter = new RouteLineDetAdapter(this, routeLines);

            routeItemsListView.setAdapter(routeLineAdapter);

        }
}
