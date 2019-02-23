package com.example.bu_bustracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import static com.example.bu_bustracker.R.id.route_line_list_view;

public class LinesFragment extends Fragment {

    ListView route;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //just change the fragment_dashboard
        //with the fragment you want to inflate
        //like if the class is HomeFragment it should have R.layout.home_fragment
        //if it is DashboardFragment it should have R.layout.fragment_dashboard
        View rootView = inflater.inflate(R.layout.lines_fragment_layout, container,false);
        //Route listView
         route = rootView.findViewById(route_line_list_view);

        Route route10 = new Route("Notullbad","University",R.drawable.ic_directions_black_24dp);
        Route route20 = new Route("Barisal Club","University",R.drawable.ic_directions_black_24dp);
        Route route30 = new Route("Natun Bazar","University",R.drawable.ic_directions_black_24dp);

        Route route01 = new Route("University","Notullbad",R.drawable.ic_directions_black_24dp);
        Route route02 = new Route("University","Barisal Club",R.drawable.ic_directions_black_24dp);
        Route route03 = new Route("University","Natun Bazar",R.drawable.ic_directions_black_24dp);

        ArrayList<Object> routes = new ArrayList<>();
        routes.add("Go to University");
        routes.add(route10);
        routes.add(route20);
        routes.add(route30);
        routes.add("Go to Home");
        routes.add(route01);
        routes.add(route02);
        routes.add(route03);

        if(route!=null){

            route.setAdapter(new RouteAdapter(this.getActivity(), routes));
        }
        return rootView;
    }
}
