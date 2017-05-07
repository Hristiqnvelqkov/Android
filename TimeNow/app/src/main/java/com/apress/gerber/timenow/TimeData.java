package com.apress.gerber.timenow;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hriso on 4/29/2017.
 */

public class TimeData {
    public static final String DATABASE_NAME="times";
    public static final String TABLE_NAME="time";
    public static final String COLUMN_NAME_ID="_id";
    public static final String COLUMN_NAME_CITY="city";
    public static final String COLUMN_NAME_UTC="utc";
    public static final String COLUMN_NAME_VERSION="version";
    private int DATABASE_VERSION=1;
    private Context mContext;
    private SQLiteDatabase mSQLiteDatabase;
    private TimeSqlHelper mHelper=null;
    private static final String SQL_CREATE="CREATE TABLE "+TABLE_NAME + "(" + COLUMN_NAME_ID + " INTEGER PRIMARY KEY autoincrement, "
            + COLUMN_NAME_CITY + " TEXT, " +COLUMN_NAME_UTC + " TEXT, " + COLUMN_NAME_VERSION +" INTEGER)";
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
    public String getUtc(String city){
        int utc;
        String utcInString=null;
        Cursor query = mSQLiteDatabase.rawQuery("Select * From time"+ " WHERE city = '" + city + "'",null);
        if(query !=null && query.getCount()>0) {
            query.moveToFirst();
            utcInString = query.getString(query.getColumnIndex("utc"));
            query.close();
        }
        return utcInString;
    }
    public void addCityTime(String city,String Utc){
        ContentValues content= new ContentValues();
        content.put(COLUMN_NAME_CITY,city);
        System.out.println(city);
        content.put(COLUMN_NAME_UTC,Utc);
        mSQLiteDatabase.insert(TABLE_NAME,null,content);
    }
    public int returnVersion() {
        int version=0;
        Cursor cursor = mSQLiteDatabase.rawQuery("Select version FROM time", null);
        if (cursor.getCount() == 0 ) {
            ContentValues values = new ContentValues();
            values.put("version", 1);
            mSQLiteDatabase.insert(TABLE_NAME, null, values);
        } else{
            cursor.moveToFirst();
            version = cursor.getInt(cursor.getColumnIndex("version"));
            cursor.close();
        }
        return version;
    }
    public void setVersion(int version){
        Cursor cursor = mSQLiteDatabase.rawQuery("Select version FROM time",null);
        if(cursor.getCount()==0){
            ContentValues contentValues = new ContentValues();
            contentValues.put("version",version);
            mSQLiteDatabase.insert(TABLE_NAME,null,contentValues);
        }else{
            mSQLiteDatabase.execSQL("Update "+TABLE_NAME +" Set version = " + version);
        }
        cursor.close();
    }
    public boolean checkExist(String city){
        Cursor cursor = mSQLiteDatabase.rawQuery("Select * From " + TABLE_NAME + " WHERE city = '" + city + "'" ,null);
        if(cursor.getCount()==0){
            cursor.close();
            return false;
        }else{
            cursor.close();
            return true;
        }
    }
    public Cursor getAll(){
        Cursor cursor = mSQLiteDatabase.rawQuery("Select * from time",null);
        if(cursor.getCount()>0)
            cursor.moveToFirst();
        return cursor;
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
