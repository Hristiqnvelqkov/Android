package com.apress.gerber.getplaces;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class ListFragment extends BaseFragment {
    ListView listView;
    private String[] bars;
    private Menu menu;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        listView = (ListView) view.findViewById(R.id.list_bars);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final String place = (parent.getItemAtPosition(position)).toString();
                PopupMenu popupMenu = new PopupMenu(getActivity(),view);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId()==R.id.showMap){
                            Toast.makeText(getContext(),place,Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                });
                popupMenu.show();

            }
        });
        bars = getArguments().getStringArray("bars");
        ArrayAdapter<String> adapter = getAdapter(bars);
        listView.setAdapter(adapter);
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
        menu.add(0,Menu.FIRST,Menu.NONE,R.string.grid);
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