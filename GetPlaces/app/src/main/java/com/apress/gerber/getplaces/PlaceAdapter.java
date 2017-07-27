package com.apress.gerber.getplaces;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;


import static com.apress.gerber.getplaces.com.apress.gerber.getplaces.fragments.BaseFragment.mListener;


public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {
    private static Place[] mDataset;
     public static class ViewHolder extends RecyclerView.ViewHolder {
         TextView mTextView;
       public  ViewHolder(View v) {
             super(v);
             mTextView = (TextView) v.findViewById(R.id.place_name);
             v.setOnClickListener(new View.OnClickListener() {
                 @Override public void onClick(View v) {
                     int pos = getAdapterPosition();
                     Place place = mDataset[pos];
                     final Place[] places = new Place[1];
                     places[0]=place;
                     PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
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

         }
     }
    // Provide a suitable constructor (depends on the kind of dataset)
    public PlaceAdapter(Place[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PlaceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v =  LayoutInflater.from(parent.getContext()).inflate(R.layout.place_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mDataset[position].getName());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}


