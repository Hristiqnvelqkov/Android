package com.apress.gerber.timenow;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.DocumentsContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by hriso on 4/28/2017.
 */

public class GetTime extends AsyncTask<String,Void,HashMap> {
    Context mContext;
    GetTime(Context context){
        mContext=context;
    }
    TimeData mData;
    HashMap cityTime = new HashMap();
    @Override
    protected HashMap doInBackground(String... params) {
        String city = params[0];
        System.out.println(city);
        mData=new TimeData(mContext);
        try {
            mData.open();
            URL mUrl = new URL("http://192.168.45.70:3000/cities"+city);
            HttpURLConnection connection= (HttpURLConnection) mUrl.openConnection();
            connection.connect();
            String inputLine;
            StringBuffer getRequest = new StringBuffer();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while((inputLine=in.readLine())!=null){
                getRequest.append(inputLine);
            }
            String getRequestToString = getRequest.toString();
            JSONArray cityZoneOffset = new JSONArray(getRequestToString);
            for(int i=0;i<cityZoneOffset.length();i++){
                JSONObject ob = cityZoneOffset.getJSONObject(i);
                String thisCity=ob.get("city").toString();
                String thisTime=ob.get("zoneOffset").toString();
                cityTime.put(thisCity,thisTime);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
       // System.out.println(cityTime);
        return cityTime;
    }
}
