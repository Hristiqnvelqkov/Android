package com.apress.gerber.timenow;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
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
    TimeData mData;
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mData=new TimeData(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] cityNames = {};
        mData.open();
        HashMap cityNameZone = new HashMap();
        GetTime cityTime = new GetTime(this);
        CheckVersion version =new CheckVersion();
        int vers = mData.returnVersion();
        try {
            int response=version.execute().get();
            if(vers < response) {
                vers=response;
                mData.setVersion(vers);
                cityNameZone = cityTime.execute("").get();
                String[] cityNameZoneInArr = cityNameZone.toString().replace("{", "").replace("}", "").split(", ");
                for (int i = 0; i < cityNameZoneInArr.length; i++) {
                    boolean exist = mData.checkExist((cityNameZoneInArr[i].split("=")[0]));
                    if (!exist) {
                        mData.addCityTime(cityNameZoneInArr[i].split("=")[0], cityNameZoneInArr[i].split("=")[1]);
                    }
                }
                ArrayAdapter<String> itemAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ParseTime.parse(cityNameZoneInArr));
                ListView list = (ListView) findViewById(R.id.listTime);
                list.setAdapter(itemAdapter);
            }else{
                Cursor cursor = mData.getAll();
                ListView listView = (ListView) findViewById(R.id.listTime);
                MuCustomCursorAdapter adapter = new MuCustomCursorAdapter(this,cursor);
                listView.setAdapter(adapter);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }finally {
            mData.close();
        }

    }
    @Override protected void onResume(){
        super.onResume();
        mData.open();
        Cursor cursor = mData.getAll();
        ListView listView = (ListView) findViewById(R.id.listTime);
        MuCustomCursorAdapter adapter = new MuCustomCursorAdapter(this,cursor);
        listView.setAdapter(adapter);
        mData.close();
    }
    @Override public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
            if(item.getItemId()==R.id.search){
                Intent intent = new Intent (this,SearchAndShowActivity.class);
                startActivity(intent);
            }
        return false;
    }
}
