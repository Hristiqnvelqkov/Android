package com.apress.gerber.timenow;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.DocumentsContract;

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
        mData=new TimeData(mContext);
        try {
            mData.open();
            URL mUrl = new URL("https://time.is/"+city);
            HttpsURLConnection connection= (HttpsURLConnection) mUrl.openConnection();
            connection.connect();
            String inputLine;
            StringBuffer getRequest = new StringBuffer();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while((inputLine=in.readLine())!=null){
                getRequest.append(inputLine);
            }
            String getRequestToString = getRequest.toString();
            Document doc = Jsoup.parse(getRequestToString);
            Elements e = doc.select("#twd");
            Elements e1 = doc.select(".keypoints");
            String timeNow=e.text();
            String utc = e1.text().split(",")[1].split(" ")[2];
            mData.addCityTime(city,utc);
            mData.close();
            in.close();
            cityTime.put(city,timeNow);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return cityTime;
    }
}
