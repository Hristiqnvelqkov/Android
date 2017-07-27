package com.apress.gerber.getplaces.com.apress.gerber.getplaces.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.apress.gerber.getplaces.Constants;
import com.apress.gerber.getplaces.com.apress.gerber.getplaces.com.apress.gerber.getplaces.tasks.GetAdresses;
import com.apress.gerber.getplaces.Place;
import com.apress.gerber.getplaces.PlaceAdapter;
import com.apress.gerber.getplaces.R;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclePlaceFragment extends BaseFragment {
    private RecyclerView recycleView;
    private Place[] places;
    private Menu menu;
    private PlaceAdapter adapter;
    TextWatcher wather = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override
        public void afterTextChanged(Editable s) {
            Place[] filteredPlaces;
            ArrayList<Place> filter = new ArrayList<>();
            if (s.toString().length() > 2) {
                for (int i = 0; i < places.length; i++) {
                    if (places[i].getName().toLowerCase().contains(s.toString().toLowerCase())) {
                        filter.add(places[i]);
                    }
                }
                filteredPlaces=new Place[filter.size()];
                filteredPlaces=filter.toArray(filteredPlaces);
                adapter = new PlaceAdapter(filteredPlaces);
            }else{
                adapter = new PlaceAdapter( places);
            }
            recycleView.setAdapter(adapter);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycle_place,container,false);
        places = (Place[]) getArguments().getParcelableArray(Constants.BARS);
        adapter = new PlaceAdapter(places);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recycleView = (RecyclerView) view.findViewById(R.id.recycle_view);
        recycleView.setLayoutManager(manager);
        recycleView.setAdapter(adapter);
        addActionBar(wather);
        return view;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu,menu);
        super.onCreateOptionsMenu(menu,inflater);
        this.menu=menu;
        menu.add(0,Menu.FIRST,Menu.NONE,R.string.grid);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case Menu.FIRST + 1:
                setLayoutManager(Constants.LISTVIEW,recycleView);
                menu.removeItem(Menu.FIRST+1);
                menu.add(0,Menu.FIRST,Menu.NONE,R.string.grid);
                break;

            case Menu.FIRST:
                setLayoutManager(Constants.GRIDVIEW,recycleView);
                menu.removeItem(Menu.FIRST);
                menu.add(0,Menu.FIRST+1,Menu.NONE,R.string.showList);
                break;
            case R.id.search:
                if (places != null)
                    mListener.addMapActivity(places);
                break;
        }

        return true;
    }
}
