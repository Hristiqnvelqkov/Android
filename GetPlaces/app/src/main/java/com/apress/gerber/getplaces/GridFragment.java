package com.apress.gerber.getplaces;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import java.util.concurrent.ExecutionException;


public class GridFragment extends BaseFragment {
    GridView mGridView ;
    View view;
    String[] bars;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu,menu);
        super.onCreateOptionsMenu(menu,inflater);
        menu.add(0,Menu.FIRST+1,Menu.NONE,R.string.showList);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bars=getArguments().getStringArray("bars");
        view=inflater.inflate(R.layout.fragment_grid, container, false);
        mGridView=(GridView)view.findViewById(R.id.grid);
        mGridView.setNumColumns(2);
        ArrayAdapter<String> adapter=getAdapter(bars);
        mGridView.setAdapter(adapter);

        return view;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==Menu.FIRST){
            mListener.changeListViewToGrid(bars);
        }
        if(item.getItemId()==Menu.FIRST+1){
            getFragmentManager().popBackStack();
        }
        if(item.getItemId()==R.id.search) {
            String[] buff=null;
            GetAdresses locations = new GetAdresses();
            try {
                buff = locations.execute(SearchFragment.location, "geometry").get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            if (buff != null)
                mListener.addMapActivity(buff);
        }
        return true;
    }
}
