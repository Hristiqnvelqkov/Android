package com.apress.gerber.dragracing;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hriso on 4/3/2017.
 */

public class UserDb {
    public static final String DATABASE_NAME="users";
    public static final String TABLE_NAME="user";
    public static final String COLUMN_NAME_ID="_id";
    public static final String COLUMN_NAME_PASS="password";
    public static final String COLUMN_NAME_NAME="username";
    private int DATABASE_VERSION=1;
    private Context mContext;
    private MySQLiteOpenHelper mHelper;
    private SQLiteDatabase DataBase;
    private static final String SQL_CREATE="CREATE TABLE "+TABLE_NAME+"("+COLUMN_NAME_ID +" INTEGER PRIMARY KEY autoincrement, "
            +COLUMN_NAME_PASS+" TEXT, "+ COLUMN_NAME_NAME + " TEXT)";
    private static final String DEL_TABLE="DROP TABLE IF EXIST "+TABLE_NAME;
    UserDb(Context context){
        this.mContext=context;
    }
    public void open(){
        mHelper=new MySQLiteOpenHelper(mContext);
        DataBase=mHelper.getWritableDatabase();
    }
    public void close(){
        mHelper.close();
    }
    public void createUser(String name,String pass){
        ContentValues content=new ContentValues();
        content.put(COLUMN_NAME_NAME,name);
        content.put(COLUMN_NAME_PASS,pass);
        DataBase.insert(TABLE_NAME,null,content);
    }
    class MySQLiteOpenHelper extends SQLiteOpenHelper{

        public MySQLiteOpenHelper(Context context) {
            super(context, DATABASE_NAME,null,DATABASE_VERSION);
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
