package com.apress.gerber.stolencars;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hriso on 2/28/2017.
 */

public class UserDb {
    public static final String DATABASE_NAME = "usersdb";
    public static final String TABLE_NAME = "user";
    public static final String COLUMN_NAME_ID = "id";
    public static final String COLUMN_NAME_EMAIL = "email";
    public static final String COLUMN_NAME_PASS = "pass";
    public static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_NAME_ID + " INTEGER PRIMARY KEY autoincrement," +
                    COLUMN_NAME_EMAIL + " TEXT," +
                    COLUMN_NAME_PASS + " TEXT)";
    private static final String TAG = "RemindersDbAdapter";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
    private DateBaseHelper mdBHelper;
    private SQLiteDatabase sqlLiteDate;
    private final Context cntx;

    public UserDb(Context cntx) {
        this.cntx = cntx;
    }

    public void open() throws SQLException {
        mdBHelper = new DateBaseHelper(cntx);
        sqlLiteDate=mdBHelper.getWritableDatabase();

    }

    public void close() {
        if (mdBHelper != null)
            mdBHelper.close();
    }

    public void createUser(String email, String pass) {
        ContentValues content = new ContentValues();
        content.put(COLUMN_NAME_EMAIL, email);
        content.put(COLUMN_NAME_PASS, pass);
        sqlLiteDate.insert(TABLE_NAME,null,content);
    }

    private static class DateBaseHelper extends SQLiteOpenHelper {
        DateBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, SQL_CREATE_ENTRIES);
            db.execSQL(SQL_CREATE_ENTRIES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
