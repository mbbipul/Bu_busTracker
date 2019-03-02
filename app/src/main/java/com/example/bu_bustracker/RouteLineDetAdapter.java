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
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class RouteLineDetAdapter extends BaseAdapter  {

    Context context;
    LayoutInflater inflater;
    ArrayList<RouteLine> routeLines;


    public RouteLineDetAdapter(Context c,ArrayList<RouteLine> mRouteLines) {
        // TODO Auto-generated constructor stub
        this.context = c;
        this.routeLines=mRouteLines;
        inflater = LayoutInflater.from(c);
    }

    //GET TOTAL NUMBER OF ITEMS IN ARRAYLIST
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return routeLines.size();
    }

    //GET A SINGLE ITEM FROM THE ARRAYLIST
    @Override
    public Object getItem(int pos) {
        // TODO Auto-generated method stub
        return routeLines.get(pos);
    }

    //GET ITEM IDENTIFIER
    @Override
    public long getItemId(int pos) {
        // TODO Auto-generated method stub
        return pos;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.list_row, parent, false);
        }

        // get current item to be displayed
        RouteLine currentItem = (RouteLine) getItem(pos);

        // get the TextView for item name and item description
        TextView routeLineName = (TextView)
                convertView.findViewById(R.id.route_line_list_text);

        //sets the text for item name and item description from the current item object
        routeLineName.setText(currentItem.getRouteName());

        // returns the view for the current row
        return convertView;
    }

}
