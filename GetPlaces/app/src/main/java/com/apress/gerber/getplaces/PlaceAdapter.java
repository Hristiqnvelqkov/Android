package com.apress.gerber.getplaces;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by hriso on 7/21/2017.
 */

public class PlaceAdapter extends ArrayAdapter<Place> {
    public PlaceAdapter(Context context, Place[] places) {
        super(context, 0 , places);
    }
    @Override
    public View getView(int position, View convertedView, ViewGroup parent){
        Place place = getItem(position);
        if(convertedView==null){
            convertedView= LayoutInflater.from(getContext()).inflate(R.layout.place_item,parent,false);
        }
        TextView view=(TextView)convertedView.findViewById(R.id.place_name);
        if(place!=null) {
            view.setText(place.getName());
        }
        return convertedView;
    }
}
