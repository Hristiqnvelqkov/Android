package com.apress.gerber.getplaces;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;


import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ListFragment extends BaseFragment {
    ListView listView;
    private Place[] places;
    PlaceAdapter adapter;
    TextWatcher wather = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
        @Override
        public void afterTextChanged(Editable s) {
            int counter = 0;
            Place[] filtred_places;
            ArrayList<Place> filter = new ArrayList<>();
            System.out.println(s.toString());
            if (s.toString().length() > 2) {
                for (int i = 0; i < places.length; i++) {
                    if (places[i].getName().toLowerCase().contains(s.toString().toLowerCase())) {
                        filter.add(places[i]);
                    }
                }
                filtred_places=new Place[filter.size()];
                for(Place place : filter){
                    filtred_places[counter]=place;
                    counter++;
                }
                adapter = new PlaceAdapter(getContext(), filtred_places);
            }else{
                adapter = new PlaceAdapter(getContext(), places);
            }
             listView.setAdapter(adapter);
        }
    };
    public void addActionBar(TextWatcher watcher){
        android.support.v7.app.ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setCustomView(R.layout.custom_actionbar);
        EditText search = (EditText) actionBar.getCustomView().findViewById(R.id.searchfield);
        places = (Place[]) getArguments().getParcelableArray("bars");
        search.addTextChangedListener(watcher);
        actionBar.setDisplayOptions(actionBar.DISPLAY_SHOW_CUSTOM
                | actionBar.DISPLAY_SHOW_HOME);
    }
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        listView = (ListView) view.findViewById(R.id.list_bars);
        addActionBar(wather);
    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
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
        adapter = new PlaceAdapter(getContext(),places);
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
            mListener.changeListViewToGrid(places);
        }
        if(item.getItemId()==Menu.FIRST+1){
            getFragmentManager().popBackStack();
        }
        if(item.getItemId()==R.id.search) {
            Place[] places=null;
            GetAdresses locations = new GetAdresses();
            try {
                places = locations.execute(SearchFragment.location).get();
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