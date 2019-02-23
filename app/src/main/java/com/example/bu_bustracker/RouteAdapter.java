/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.bu_bustracker;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RouteAdapter extends BaseAdapter {

    ArrayList<Object> routes;
    Context c;
    LayoutInflater inflater;
    static final int ROW=0;
    static final int HEADER=1;

    public RouteAdapter(Context c,ArrayList<Object> players) {
        // TODO Auto-generated constructor stub
        this.c=c;
        this.routes=players;
    }

    //GET TOTAL NUMBER OF ITEMS IN ARRAYLIST
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return routes.size();
    }

    //GET A SINGLE ITEM FROM THE ARRAYLIST
    @Override
    public Object getItem(int pos) {
        // TODO Auto-generated method stub
        return routes.get(pos);
    }

    //GET ITEM IDENTIFIER
    @Override
    public long getItemId(int pos) {
        // TODO Auto-generated method stub
        return pos;
    }

    @Override
    public int getItemViewType(int position) {

        //CHECK IF CURRENT ITEM IS PLAYER THEN RETURN ROW
        if(getItem(position) instanceof Route)
        {
            return ROW;
        }

        //OTHERWISE RETURN HEADER
        return HEADER;
    }

    @Override
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return 2;
    }
    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        //TYPE OF VIEW
        int type=getItemViewType(pos);

        //IF THERE IS NO VIEW CREATE IT
        if(convertView==null)
        {
            inflater=(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            switch (type) {
                case ROW:
                    convertView=inflater.inflate(R.layout.lines_route_item, null);
                    break;
                case HEADER:
                    convertView=inflater.inflate(R.layout.lines_route_header,null);
                default:
                    break;
            }
        }

        //OTHERWISE CHECK IF ITS ROW OR HEADER AND SET DATA ACCORDINGLY
        switch (type) {
            case ROW:
                Route r=(Route) getItem(pos);

                //INITIALIZE TEXTVIEW AND IMAGEVIEW
                TextView routeDes=(TextView) convertView.findViewById(R.id.route_des);
                ImageView img=(ImageView)convertView.findViewById(R.id.route_image);

                //SET TEXT AND IMAGE
                routeDes.setText(r.getDeparturePlace() + " - " +r.getDestinitionPlace());
                img.setImageResource(r.getIconSrc());

                break;
            case HEADER:
                String header=(String) getItem(pos);
                TextView headerTv=(TextView) convertView.findViewById(R.id.header_title);

                //SET HEADER TEXT AND MAYBE BACKGROUND
                headerTv.setText(header);
            default:
                break;
        }

        return convertView;
    }

}
