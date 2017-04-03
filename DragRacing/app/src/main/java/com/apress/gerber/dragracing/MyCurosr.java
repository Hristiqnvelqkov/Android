package com.apress.gerber.dragracing;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apress.gerber.dragracing.R;

/**
 * Created by hriso on 4/1/2017.
 */

public class MyCurosr extends CursorAdapter {
    public MyCurosr(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.event_row,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String username=cursor.getString(cursor.getColumnIndex("title"));
        TextView textView=(TextView) view.findViewById(R.id.eventName);
        textView.setText(username);
    }
}
