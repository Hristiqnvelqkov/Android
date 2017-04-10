package com.apress.gerber.dragracing;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by hriso on 4/6/2017.
 */

public class UserEventsCursor extends CursorAdapter {
    UserEventsCursor(Context context,Cursor c){
        super(context,c,0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.user_event_raw,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textView=(TextView) view.findViewById(R.id.userEvent);
        String text=cursor.getString(cursor.getColumnIndex("title"));
        textView.setText(text);
    }
}
