package com.apress.gerber.getplaces.com.apress.gerber.getplaces.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;

import com.apress.gerber.getplaces.Constants;
import com.apress.gerber.getplaces.Place;
import com.apress.gerber.getplaces.R;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BaseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class BaseFragment extends Fragment {

    public static OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void addActionBar(TextWatcher watcher){
        android.support.v7.app.ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setCustomView(R.layout.custom_actionbar);
        EditText search = (EditText) actionBar.getCustomView().findViewById(R.id.searchfield);
        search.addTextChangedListener(watcher);
        actionBar.setDisplayOptions(actionBar.DISPLAY_SHOW_CUSTOM
                | actionBar.DISPLAY_SHOW_HOME);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    public void setLayoutManager(String view,RecyclerView reView){
        RecyclerView.LayoutManager manager=null;
        if(view.equals(Constants.GRIDVIEW)){
            manager = new GridLayoutManager(getContext(),2);
        }
        if(view.equals(Constants.LISTVIEW)){
            manager = new LinearLayoutManager(getContext());
        }
        if(manager!=null) {
            reView.setLayoutManager(manager);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void sendBarsToActivity(Place[] bars);
        void addMapActivity(Place[] buff);
    }
}
