package com.example.bu_bustracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SpreadLoveAdapter extends BaseAdapter {
    Context context;
    String mediaList[];
    int mediaIcon[];
    LayoutInflater inflter;

    public SpreadLoveAdapter(Context applicationContext, String[] countryList, int[] mediaIcon) {
        this.context = context;
        this.mediaList = countryList;
        this.mediaIcon = mediaIcon;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return mediaList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.spread_love_item_list, null);
        TextView country = view.findViewById(R.id.textView);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        country.setText(mediaList[i]);
        icon.setImageResource(mediaIcon[i]);
        return view;
    }

}
