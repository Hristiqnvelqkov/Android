package com.apress.gerber.getplaces.com.apress.gerber.getplaces.activities;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.apress.gerber.getplaces.Constants;
import com.apress.gerber.getplaces.com.apress.gerber.getplaces.fragments.BaseFragment;
import com.apress.gerber.getplaces.Place;
import com.apress.gerber.getplaces.R;
import com.apress.gerber.getplaces.com.apress.gerber.getplaces.fragments.RecyclePlaceFragment;
import com.apress.gerber.getplaces.com.apress.gerber.getplaces.fragments.SearchFragment;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity implements BaseFragment.OnFragmentInteractionListener {
    FragmentManager manager = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(manager.findFragmentByTag(Constants.LIST_TAG)==null) {
            if(manager.findFragmentByTag(Constants.SEARCH_TAG)==null) {
                Fragment search = new SearchFragment();
                commitFragment(search, false,Constants.SEARCH_TAG);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
    }
    public void commitFragment(Fragment fragment, boolean addBackStack,String tag){
        FragmentTransaction transaction = manager.beginTransaction();
        if(addBackStack){
            transaction.addToBackStack(tag);
        }
        if(fragment instanceof SearchFragment){
            transaction.add(fragment,tag);
        }else{
            transaction.replace(R.id.activity_main,fragment,tag);
        }

        transaction.commit();
    }
    @Override
    public void sendBarsToActivity(Place[] bars) {
        Fragment listBars = new RecyclePlaceFragment();
        Bundle args = new Bundle();
        args.putParcelableArray(Constants.BARS,bars);
        listBars.setArguments(args);
        commitFragment(listBars,true,Constants.LIST_TAG);
    }

    @Override
    public void addMapActivity(Place[] buff) {
        ArrayList<Place> places = new ArrayList<Place>(Arrays.asList(buff));
        Intent intent = new Intent(this,MapActivity.class);
        intent.putParcelableArrayListExtra(Constants.BARS,places);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        Fragment listView = manager.findFragmentByTag(Constants.LIST_TAG);
        Fragment search = manager.findFragmentByTag(Constants.SEARCH_TAG);
        Bundle bundle= new Bundle();
        if(listView!=null){
            search.onCreate(bundle);
        }else
             super.onBackPressed();
   }
}
