package com.apress.gerber.login;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.content.LocalBroadcastManager;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by hriso on 6/28/2017.
 */

public class FetchAddressIntentService extends IntentService {
    public static final String BROADCAST_ACTION="com.example.android.FetchAddressIntentService.BROADCAST";
    public static final String EXTENDED_DATA_STATUS="com.example.android.FetchAddressIntentService.STATUS";
    public FetchAddressIntentService(String name) {
        super(name);
    }
    public FetchAddressIntentService(){
        super("FetchAddressIntentServize");
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        List<Address> addresses=null;
        String cityName=intent.getStringExtra(LogActivity.CITY_EXTRA);
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        StringBuffer addressesBuf = new StringBuffer();
        try {
            addresses = geocoder.getFromLocationName(cityName,5);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(addresses!=null) {
            for (Address address : addresses) {
                addressesBuf.append(address.getFeatureName());
                addressesBuf.append(",");
            }
        }
        Intent broadcast = new Intent();
        broadcast.setAction(BROADCAST_ACTION);
        broadcast.putExtra(EXTENDED_DATA_STATUS,addressesBuf.toString());
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcast);
    }
}
