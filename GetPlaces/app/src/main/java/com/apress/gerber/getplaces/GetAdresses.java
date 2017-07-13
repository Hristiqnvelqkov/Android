package com.apress.gerber.getplaces;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

/**
 * Created by hriso on 7/12/2017.
 */

class GetAdresses extends AsyncTask<String,Void,String> {
    @Override
    protected String doInBackground(String... params) {
        String LatLngt = params[0];
        LatLngt = LatLngt.split(Pattern.quote("("))[1];
        LatLngt = LatLngt.split(Pattern.quote(")"))[0];
        StringBuffer getRequest = new StringBuffer();
        final String ENDPOINT = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+LatLngt+"&radius=10000&type=bar&key=AIzaSyBTpQv1lJAxORmRgnI9WAIs9jCw9n9QVag";
        try {
            URL mUrl = new URL(ENDPOINT);
            HttpURLConnection httpURLConnection = (HttpURLConnection) mUrl.openConnection();
            httpURLConnection.setRequestMethod("GET");
            String inputLine;
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            while((inputLine=in.readLine())!=null){
                getRequest.append(inputLine);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getRequest.toString();
    }
}
