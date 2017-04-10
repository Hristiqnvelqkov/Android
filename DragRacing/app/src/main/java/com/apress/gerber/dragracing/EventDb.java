package com.apress.gerber.dragracing;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hriso on 4/1/2017.
 */

public class EventDb {
    public static final String DATABASE_NAME = "events";
    public static final String TABLE_NAME = "event";
    public static final String COLUMN_NAME_ID = "_id";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_CREATOR = "user_id";
    public static final int DATABASE_VERSION = 1;
    public static final String COLUMN_NAME_DESCRIPTION = "description";
    private static final String SQL_CREATE = "CREATE TABLE event (_id INTEGER PRIMARY KEY autoincrement, title TEXT, description Text," +
            " user_id INTEGER )";
    private static final String DELETE_SQL = "DROP TABLE IF EXIST event";
    private SQLiteDatabase DataBase;
    private SqlHelper mSqlHelper;
    private Context mContext;

    public EventDb(Context context) {
        this.mContext = context;
    }

    public void open() {
        mSqlHelper = new SqlHelper(mContext);
        DataBase = mSqlHelper.getWritableDatabase();
    }

    public void createEvent(String title, String description, UserModel user) {
        ContentValues content = new ContentValues();
        content.put(COLUMN_NAME_TITLE, title);
        content.put(COLUMN_NAME_DESCRIPTION, description);
        content.put(COLUMN_NAME_CREATOR, user.getId());
        DataBase.insert(TABLE_NAME, null, content);

    }

    public void deleteEvent(int id) {
        DataBase.execSQL("DELETE FROM event WHERE _id = " + id);
    }

    public void updateEvent(int id, String newTitle, String newDesc) {
        DataBase.execSQL("UPDATE event SET " + COLUMN_NAME_TITLE + " = '" + newTitle + "'"+
                ","+COLUMN_NAME_DESCRIPTION+" = '" + newDesc + "'"+" WHERE _id = "+id+";");
    }

    public Cursor getAllEvents() {
        return DataBase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public Cursor userEvents(UserModel user) {
        Cursor query = DataBase.rawQuery("Select * from " + TABLE_NAME + " WHERE user_id = " + user.getId(), null);
        query.moveToFirst();
        return query;
    }

    public void close() {
        mSqlHelper.close();
    }

    private class SqlHelper extends SQLiteOpenHelper {

        public SqlHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DELETE_SQL);
            onCreate(db);
        }
    }
}
