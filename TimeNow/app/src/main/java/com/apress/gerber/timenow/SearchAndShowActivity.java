package com.apress.gerber.timenow;

import android.database.Cursor;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class SearchAndShowActivity extends AppCompatActivity {

    TimeData mTimeData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_and_show);
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        Fragment frag= fragmentManager.findFragmentByTag("show");
        if(frag==null){
            Fragment fragment = new SearchCity();
            transaction.add(R.id.activity_search_and_show,fragment,"search");
            transaction.commit();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void searchTime(View view) throws ExecutionException, InterruptedException {
        HashMap cityTime = new HashMap();
        mTimeData=new TimeData(this);
        mTimeData.open();
        EditText cityName = (EditText) findViewById(R.id.nameOfCity);
        String nameOfCity = cityName.getText().toString();
        String utc = mTimeData.getUtc(nameOfCity);
        if(utc!=null) {
            String[] cityTimeInArr = new String[1];
            cityTimeInArr[0] = nameOfCity + "=" + utc;
            String[] response = ParseTime.parse(cityTimeInArr);
            String timeNow = response[0].split("=")[1];
            mTimeData.close();
            FragmentManager manager = getSupportFragmentManager();
            Fragment fragment = manager.findFragmentByTag("search");
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.addToBackStack("search");
            transaction.remove(fragment);
            Fragment time1 = new Time();
            transaction.add(R.id.activity_search_and_show, time1, "show");
            Bundle bundle = new Bundle();
            bundle.putString("city", nameOfCity);
            bundle.putString("time", timeNow);
            time1.setArguments(bundle);
            transaction.commit();
        }else{
            Toast.makeText(this, "This city doesn't exist",
                    Toast.LENGTH_LONG).show();
        }
        //city.setText(cityTime.keySet().toArray()[0].toString());
        //.setText(cityTime.values().toArray()[0].toString());

    }
}


