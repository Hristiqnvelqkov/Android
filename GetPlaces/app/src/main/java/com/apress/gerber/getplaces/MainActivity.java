package com.apress.gerber.getplaces;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements BaseFragment.OnFragmentInteractionListener {
    FragmentManager manager = getSupportFragmentManager();
    public static final String SEARCH_TAG="search_form";
    public static final String LIST_TAG="lists";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(manager.findFragmentByTag(LIST_TAG)==null) {
            if(manager.findFragmentByTag(SEARCH_TAG)==null) {
                Fragment search = new SearchFragment();
                commitFragment(search, false,SEARCH_TAG);
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
    public void sendBarsToActivity(String[] bars) {
        Fragment listBars = new ListFragment();
        Bundle args = new Bundle();
        args.putStringArray("bars",bars);
        listBars.setArguments(args);
        commitFragment(listBars,true,LIST_TAG);
    }

    @Override
    public void addMapActivity(String[] buff) {
        Intent intent = new Intent(this,MapActivity.class);
        intent.putExtra("locations",buff);
        startActivity(intent);
    }
    @Override
    public void changeListViewToGrid(String[] bars){
        Fragment grid = new GridFragment();
        Bundle args = new Bundle();
        args.putStringArray("bars",bars);
        grid.setArguments(args);
        commitFragment(grid,true,"gridFragment");
    }
}
