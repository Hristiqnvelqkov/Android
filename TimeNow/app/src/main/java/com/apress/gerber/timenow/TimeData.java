package com.apress.gerber.timenow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hriso on 4/29/2017.
 */

public class TimeData {
    public static final String DATABASE_NAME="times";
    public static final String TABLE_NAME="time";
    public static final String COLUMN_NAME_ID="_id";
    public static final String COLUMN_NAME_CITY="city";
    public static final String COLUMN_NAME_UTC="utc";
    private int DATABASE_VERSION=1;
    Context mContext;
    SQLiteDatabase mSQLiteDatabase;
    TimeSqlHelper mHelper=null;
    private static final String SQL_CREATE="CREATE TABLE "+TABLE_NAME + "(" + COLUMN_NAME_ID + " INTEGER PRIMARY KEY autoincrement, "
            + COLUMN_NAME_CITY + " TEXT, " +COLUMN_NAME_UTC + " TEXT)";
    private static final String DELETE_TABLE = "DROP TABLE IF EXIST " + TABLE_NAME;
    TimeData(Context context){
        mContext=context;
    }
    public void open(){
        mHelper =new TimeSqlHelper(mContext);
        mSQLiteDatabase = mHelper.getWritableDatabase();
    }
    public void close() {
        if (mHelper != null){
            mHelper.close();
        }
    }
    public void addCityTime(String city,String Utc){
        ContentValues content= new ContentValues();
        content.put(COLUMN_NAME_CITY,city);
        content.put(COLUMN_NAME_UTC,Utc);
        mSQLiteDatabase.insert(TABLE_NAME,null,content);
    }
    public boolean checkExist(String city){
        Cursor cursor = mSQLiteDatabase.rawQuery("Select * From " + TABLE_NAME + " WHERE city = '" + city + "'" ,null);
        if(cursor.getCount()==0){
            return false;
        }else{
            return true;
        }
    }
    public Cursor getCityTime(String city){
        Cursor cursor=mSQLiteDatabase.rawQuery("Select * From " + TABLE_NAME + " WHERE city = '" + city + "'" ,null);
        cursor.moveToFirst();
        return  cursor;
    }
    private class TimeSqlHelper extends SQLiteOpenHelper{

        public TimeSqlHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_CREATE);
            onCreate(db);
        }
    }
}
