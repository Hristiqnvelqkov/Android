package com.apress.gerber.getplaces;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.LinkedList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{
    List<LatLng> markers = new LinkedList<>();
    String[] locations;
    float[] Lang;
    float[] Lat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locations=getIntent().getStringArrayExtra("locations");
        Lang=new float[locations.length];
        Lat=new float[locations.length];
        for(int i=0;i<locations.length;i++){
            Lang[i]=Float.parseFloat(locations[i].split("=")[0]);
            Lat[i]=Float.parseFloat(locations[i].split("=")[1]);
            markers.add(new LatLng(Lang[i],Lat[i]));
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
