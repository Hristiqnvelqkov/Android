package com.apress.gerber.getplaces.com.apress.gerber.getplaces.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.apress.gerber.getplaces.Constants;
import com.apress.gerber.getplaces.Place;
import com.apress.gerber.getplaces.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{
    List<LatLng> markers = new LinkedList<>();
    ArrayList<Place> locations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locations=getIntent().getParcelableArrayListExtra(Constants.BARS);
        for(Place place : locations){
            markers.add(new LatLng(place.getLat(),place.getLng()));
        }
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MarkerOptions options = new MarkerOptions();
        for(LatLng point : markers){
            options.position(point);
            googleMap.addMarker(options);
        }
    }
}
