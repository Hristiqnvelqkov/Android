package com.apress.gerber.timenow;

import android.annotation.TargetApi;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.Array;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity  {

    TimeData mTimeData;
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] cityNames = {"America/Chicago", "Europe/Moscow", "Europe/London","Asia/Tokyo",
                "Europe/Paris","America/New_York","Europe/Sofia","Asia/Beijing","Australia/Sydney","Africa/Cairo"};
        HashMap cityNameTime = new HashMap();
        for (String name : cityNames) {
            TimeZone tz = TimeZone.getTimeZone(name);
            Calendar c = Calendar.getInstance(tz);
            cityNameTime.put(name,(c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)));
        }
        String times=cityNameTime.toString();
        times=times.replace("{","");
        times=times.replace("}","");
        String[] cityTimesInArray=times.split(",");
        String ListCitiesAndTimes[] = new String[10];
        for(int i=0;i<cityTimesInArray.length;i++){
           String[] tokens= cityTimesInArray[i].split("/");
            ListCitiesAndTimes[i]=(tokens[1]);
        }

        ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ListCitiesAndTimes);
        ListView list = (ListView) findViewById(R.id.listTime);
        list.setAdapter(itemAdapter);
    }
    @Override public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
            if(item.getItemId()==R.id.search){
                ListView listView = (ListView) findViewById(R.id.listTime);
                listView.setVisibility(View.GONE);
                FragmentManager fragmentManager= getSupportFragmentManager();
                Fragment fragment = new SearchCity();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.add(R.id.activity_main,fragment,"search");
                transaction.commit();
            }
        return false;
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void searchTime(View view) throws ExecutionException, InterruptedException {
        HashMap cityTime = new HashMap();
        mTimeData=new TimeData(this);
        mTimeData.open();
        EditText cityName = (EditText) findViewById(R.id.nameOfCity);
        String nameOfCity = cityName.getText().toString().replace(" ","_").toLowerCase();
        boolean exist=mTimeData.checkExist(nameOfCity);
        if(exist){
            Cursor cursor = mTimeData.getCityTime(nameOfCity);
            String utc=cursor.getString(cursor.getColumnIndex("utc"));
            int minute=0;
            int hour=0;
            int currentHour=0;
            int minutes;
            TimeZone tz = TimeZone.getTimeZone("america/casablanca");
            Calendar c = Calendar.getInstance(tz);
            if(utc.contains(":")){
                String[] utcArr = utc.split(":");
                hour=Integer.parseInt(utcArr[0]);
                minute=Integer.parseInt(utcArr[1]);
                if((currentHour=c.get(Calendar.HOUR_OF_DAY)+hour)>=24){
                    currentHour=currentHour-24;
                }
                if(hour<0){
                    minutes = c.get(Calendar.MINUTE)-minute;
                    if(minutes<0){
                        minutes=60-minute;
                        hour-=1;
                    }
                }
            }else {
                hour = Integer.parseInt(utc);
                if((currentHour=c.get(Calendar.HOUR_OF_DAY)+hour)>=24){
                    currentHour=currentHour-24;
                }
            }
            minutes=c.get(Calendar.MINUTE)+minute;
            if(minutes>=60){
                minutes=minutes-60;
                currentHour+=1;
            }
            cityTime.put(cityName.getText().toString(),currentHour+":"+minutes);
            System.out.println(cityTime);
        }else {
            GetTime time = new GetTime(this);
            cityTime=time.execute(nameOfCity).get();
            //cityTime=time.getCityTime();
            System.out.println(cityTime);
        }
        System.out.println(cityTime);
        mTimeData.close();
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = manager.findFragmentByTag("search");
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack("search");
        transaction.remove(fragment);
        Fragment time = new Time();
        transaction.add(R.id.activity_main,time);
        Bundle bundle = new Bundle();
        bundle.putString("city",cityTime.keySet().toArray()[0].toString());
        bundle.putString("time",cityTime.values().toArray()[0].toString());
        time.setArguments(bundle);
        transaction.commit();
        //city.setText(cityTime.keySet().toArray()[0].toString());
        //timeNow.setText(cityTime.values().toArray()[0].toString());

    }
}
