package com.apress.gerber.getplaces;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupMenu;

import java.util.ArrayList;


public class GridFragment extends ListFragment {
    GridView mGridView ;
    Place[] places;
    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override
        public void afterTextChanged(Editable s) {
            int counter = 0;
            ArrayList<Place> filter = new ArrayList<>();
            System.out.println(s.toString());
            if (s.toString().length() > 2) {
                for (int i = 0; i < places.length; i++) {
                    if (places[i].getName().toLowerCase().contains(s.toString().toLowerCase())) {
                        filter.add(places[i]);
                    }
                }
                Place[] filtred_places=new Place[filter.size()];
                for(Place place : filter){
                    filtred_places[counter]=place;
                    counter++;
                }
                adapter = new PlaceAdapter(getContext(),filtred_places);
            }else{
                adapter = new PlaceAdapter(getContext(), places);
            }
            mGridView.setAdapter(adapter);
        }
    };
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu,menu);
        super.onCreateOptionsMenu(menu,inflater);
        menu.add(0,Menu.FIRST+1,Menu.NONE,R.string.showList);
        menu.removeItem(Menu.FIRST);
        menu.removeItem(R.id.search);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_grid, container, false);
        mGridView = (GridView) view.findViewById(R.id.grid);
        addActionBar(watcher);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Place place = (Place) parent.getItemAtPosition(position);
                final Place[] places = new Place[1];
                places[0]=place;
                PopupMenu popupMenu = new PopupMenu(getActivity(), view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.showMap) {
                            mListener.addMapActivity(places);
                        }
                        if(item.getItemId()==R.id.popDetails){

                        }
                        return false;
                    }
                });
                popupMenu.show();

            }
        });
        places = (Place[]) getArguments().getParcelableArray("bars");
        PlaceAdapter adapter = new PlaceAdapter(getContext(),places);
        mGridView.setAdapter(adapter);
        return view;
    }
}
