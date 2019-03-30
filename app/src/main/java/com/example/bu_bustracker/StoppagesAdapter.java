package com.example.bu_bustracker;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;

public class StoppagesAdapter extends RecyclerView.Adapter<StoppagesAdapter.ViewHolder>{

    private ArrayList<Stoppages> listdata;
    private Context context;
    private int mExpandedPosition = -1;
    private int previousExpandedPosition =-1;
    private String routesname;
    View listItem;

    // RecyclerView recyclerView;
    public StoppagesAdapter(Context mContext,ArrayList<Stoppages> listdata,String routeName) {
        this.context = mContext;
        this.listdata = listdata;
        this.routesname = routeName;
    }
    @Override
    public StoppagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        listItem= layoutInflater.inflate(R.layout.stoppage_full_schedule_item, parent, false);

        StoppagesAdapter.ViewHolder viewHolder = new StoppagesAdapter.ViewHolder(listItem);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(StoppagesAdapter.ViewHolder holder, int position) {
        final Stoppages myListData = listdata.get(position);
//        TextView textView = holder.bus_names;
//        textView.setText("hello");
        holder.arrival_time.setText(listdata.get(position).getArrivalTime());
        holder.bus_names.setText(listdata.get(position).getBusNames());
        holder.routeName.setText(listdata.get(position).getRouteName());

    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView arrival_time;
        public LinearLayout busNameAll;
        public TextView bus_names;
        public TextView routeName;

        public ViewHolder(View itemView) {
            super(itemView);
            this.arrival_time = itemView.findViewById(R.id.arrival_time);
            this.busNameAll = (LinearLayout) itemView.findViewById(R.id.bus_name_all);
            this.bus_names = (TextView) itemView.findViewById(R.id.bus_names1);
            this.routeName = itemView.findViewById(R.id.route_name);
        }
    }
}
