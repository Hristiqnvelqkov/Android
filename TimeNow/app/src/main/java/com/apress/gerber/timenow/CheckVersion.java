package com.apress.gerber.timenow;

import android.content.Intent;
import android.os.AsyncTask;

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

/**
 * Created by hriso on 5/3/2017.
 */

class CheckVersion extends AsyncTask<Void,Void,Integer> {
    @Override
    protected Integer doInBackground(Void... params) {
        HttpURLConnection connection;
        int versn=0;
        try {
            URL url = new URL("http://192.168.45.70:3000/version");
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer buff = new StringBuffer();
            String line;
            while((line=in.readLine())!=null){
              buff.append(line);
            }
            JSONArray array = new JSONArray(buff.toString());
            JSONObject version = array.getJSONObject(0);
            versn = (int) version.get("id");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            return versn;
        }
    }
}
