package com.apress.gerber.parsejson;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by hriso on 3/7/2017.
 */

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>

{
    String[] mDataset;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            // Find the TextView in the LinearLayout
            mTextView =(TextView) v.findViewById(R.id.textView);
        }
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.each_row, parent, false);
        // Give the view as it is
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mTextView.setText(mDataset[position]);

    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    MyAdapter(String[] myDataset) {
        mDataset = myDataset;
    }
}