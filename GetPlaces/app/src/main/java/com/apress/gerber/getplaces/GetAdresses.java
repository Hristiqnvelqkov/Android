package com.apress.gerber.getplaces;

import android.os.AsyncTask;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

/**
 * Created by hriso on 7/12/2017.
 */

class GetAdresses extends AsyncTask<String,Void,String[]> {
    private String buff[];

    @Override
    protected String[] doInBackground(String... params) {
        String LatLngt = params[0];
        String search_for = params[1];
        HashMap<String,String> map = new HashMap<String,String>();
        LatLngt = LatLngt.split(Pattern.quote("("))[1];
        LatLngt = LatLngt.split(Pattern.quote(")"))[0];
        StringBuffer getRequest = new StringBuffer();
        StringBuffer cootdinates = new StringBuffer();
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
            JSONObject barsJson = new JSONObject(getRequest.toString());
            JSONArray jsonArray = (barsJson.getJSONArray("results"));
            buff = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject explrObject = jsonArray.getJSONObject(i);
                buff[i] = (explrObject.get(search_for).toString());
            }
            if(search_for.equals("geometry")){
                for(int i=0;i<buff.length;i++){
                    String LatLang = buff[i].split(Pattern.quote("}"))[0];
                    LatLang=LatLang.split(Pattern.quote("{"))[2];
                    String Lat = LatLang.split(",")[0];
                    Lat=Lat.split(":")[1];
                    String Lag=LatLang.split(",")[1];
                    Lag=Lag.split(":")[1];
                    map.put(Lat,Lag);
                }
                String string = map.toString().replace("{","").replace("}","");
                buff=string.split(",");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return buff;
    }
}
