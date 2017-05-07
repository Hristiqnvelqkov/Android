package com.apress.gerber.timenow;

import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashMap;

/**
 * Created by hriso on 5/4/2017.
 */

public class MuCustomCursorAdapter extends CursorAdapter {
    MuCustomCursorAdapter(Context context,Cursor cursor){
       super(context,cursor,0);
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.custom_adapter,parent,false);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView item = (TextView) view.findViewById(R.id.textView2);
        String city = cursor.getString(cursor.getColumnIndex("city"));
        TextView time = (TextView) view.findViewById(R.id.time_now);
        String utcInString = cursor.getString(cursor.getColumnIndex("utc"));
        String[] buff = new String[1];
        buff[0]=city+"="+utcInString;
        if(city!=null && time!=null) {
            System.out.println(buff[0]);
            String[] response = ParseTime.parse(buff);
            String timeNow = response[0].split("=")[1];
            time.setText(timeNow);
            item.setText(city);
        }
    }
}
