package com.apress.gerber.getplaces;


import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;


import java.util.concurrent.ExecutionException;

import static android.app.Activity.RESULT_OK;


public class SearchFragment extends BaseFragment {
    final static int PLACE_AUTOCOMPLETE_REQUEST_CODE =1;
    static String location;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getFragmentManager().findFragmentByTag(MainActivity.LIST_TAG)==null) {
            try {
                Intent intent =
                        new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                                .build(getActivity());
                startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
            } catch (GooglePlayServicesRepairableException e) {
                e.printStackTrace();
            } catch (GooglePlayServicesNotAvailableException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        com.apress.gerber.getplaces.Place[] buff;
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getContext(), data);
                GetAdresses get = new GetAdresses();
                location=place.getLatLng().toString();
                try {
                    buff=  get.execute(location).get();
                    sendBars(buff);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    public void sendBars(com.apress.gerber.getplaces.Place[] bars){
        mListener.sendBarsToActivity(bars);
    }
}
