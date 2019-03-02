package com.example.bu_bustracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

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
        //RouteModule listView
         route = rootView.findViewById(route_line_list_view);

        Routes routes10 = new Routes("Notullbad","University",R.drawable.ic_directions_black_24dp);
        Routes routes20 = new Routes("Barisal Club","University",R.drawable.ic_directions_black_24dp);
        Routes routes30 = new Routes("Natun Bazar","University",R.drawable.ic_directions_black_24dp);

        Routes routes01 = new Routes("University","Notullbad",R.drawable.ic_directions_black_24dp);
        Routes routes02 = new Routes("University","Barisal Club",R.drawable.ic_directions_black_24dp);
        Routes routes03 = new Routes("University","Natun Bazar",R.drawable.ic_directions_black_24dp);

        ArrayList<Object> routes = new ArrayList<>();
        routes.add("Go to University");
        routes.add(routes10);
        routes.add(routes20);
        routes.add(routes30);
        routes.add("Go to Home");
        routes.add(routes01);
        routes.add(routes02);
        routes.add(routes03);

        if(route!=null){

            route.setAdapter(new RouteAdapter(this.getActivity(), routes));

        }
        return rootView;
    }
}
