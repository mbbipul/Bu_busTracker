package com.example.bu_bustracker;
import android.app.MediaRouteButton;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bu_bustracker.R;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.ArrayList;

public class RouteLineStoppageAdapter extends RecyclerView.Adapter<RouteLineStoppageAdapter.ViewHolder>{
    private ArrayList<RouteLineBusStoppagesDetails> listdata;
    private Context context;
    private int mExpandedPosition = -1;
    private int previousExpandedPosition =-1;
    private String routesname;

    // RecyclerView recyclerView;
    public RouteLineStoppageAdapter(Context mContext,ArrayList<RouteLineBusStoppagesDetails> listdata,String routeName) {
        this.context = mContext;
        this.listdata = listdata;
        this.routesname = routeName;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.route_lines_details_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final RouteLineBusStoppagesDetails myListData = listdata.get(position);
        holder.route_stoppage_name.setText(listdata.get(position).getName());

        if (position==0){
        }
        final boolean isExpanded = position==mExpandedPosition;

        holder.stoppage_details.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.full_schedule.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.stoppage_bus_arrival_time1.setVisibility(isExpanded?View.VISIBLE:View.GONE);
        holder.stoppage_bus_arrival_time2.setVisibility(isExpanded?View.VISIBLE:View.GONE);

        holder.itemView.setActivated(isExpanded);

        if (isExpanded)
            previousExpandedPosition = position;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mExpandedPosition = isExpanded ? -1:position;
                notifyItemChanged(previousExpandedPosition);
                notifyItemChanged(position);
            }
        });
        holder.full_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stoppageSchedule = new Intent(context,StoppagesFullSchedule.class);
                stoppageSchedule.putExtra("routeName",routesname);
                stoppageSchedule.putExtra("stoppageName",holder.route_stoppage_name.getText());
                context.startActivity(stoppageSchedule);
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView route_stoppage_name;
        public TextView stoppage_details;
        public TimelineView timelineView;
        public TextView stoppage_bus_arrival_time1;
        public TextView stoppage_bus_arrival_time2;
        public TextView full_schedule;

        public ViewHolder(View itemView) {
            super(itemView);
            this.timelineView = itemView.findViewById(R.id.time_marker);
            this.stoppage_details = itemView.findViewById(R.id.stoppage_details);
            this.route_stoppage_name = (TextView) itemView.findViewById(R.id.route_stoppage_name);
            this.stoppage_bus_arrival_time1 = itemView.findViewById(R.id.stoppage_bus_arrival_time1);
            this.stoppage_bus_arrival_time2 = itemView.findViewById(R.id.stoppage_bus_arrival_time2);
            this.full_schedule = itemView.findViewById(R.id.full_schedule);
        }
    }
}