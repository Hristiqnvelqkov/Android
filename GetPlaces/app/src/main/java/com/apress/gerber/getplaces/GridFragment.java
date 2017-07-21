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
    Place[] places;
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
        places=(Place[]) getArguments().getParcelableArray("bars");
        view=inflater.inflate(R.layout.fragment_grid, container, false);
        mGridView=(GridView)view.findViewById(R.id.grid);
        mGridView.setNumColumns(2);
        PlaceAdapter adapter = new PlaceAdapter(getContext(),places);
        mGridView.setAdapter(adapter);
        return view;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==Menu.FIRST){
            mListener.changeListViewToGrid(places);
        }
        if(item.getItemId()==Menu.FIRST+1){
            getFragmentManager().popBackStack();
        }
        if(item.getItemId()==R.id.search) {
            Place[] places=null;
            GetAdresses locations = new GetAdresses();
            try {
                places = locations.execute(SearchFragment.location, "geometry").get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            if (places != null)
                mListener.addMapActivity(places);
        }
        return true;
    }
}
