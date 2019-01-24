package com.example.justin.fitchburgline;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class StationAdapter extends ArrayAdapter<Station> {

    private static final String LOG_TAG = StationAdapter.class.getSimpleName();

    public StationAdapter(Activity context, ArrayList<Station> stations) {
        super(context, 0, stations);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_array_list, parent,
                    false);
        }

        Station currentStation = getItem(position);

        TextView trainStation = (TextView) listItemView.findViewById(R.id.station);

        trainStation.setText(currentStation.getmStationName());

        TextView trainLine = (TextView) listItemView.findViewById(R.id.line);

        trainLine.setText(currentStation.getmRailLine());

//        View linearLayout = listItemView.findViewById(R.id.linear_layout);
//
//        linearLayout.setBackgroundColor(listItemView.getResources().getColor(mColor));

        return listItemView;
    }

}
