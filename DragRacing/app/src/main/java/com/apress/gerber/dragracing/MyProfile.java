package com.apress.gerber.dragracing;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class MyProfile extends AppCompatActivity {
    UserModel currentUser;
    EventDb mEventDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        currentUser=getIntent().getParcelableExtra("currentUser");
        mEventDb=new EventDb(this);
        mEventDb.open();
        Cursor cursor =mEventDb.userEvents(currentUser);
        ListView list=(ListView) findViewById(R.id.userEvents);
        UserEventsCursor eventsCursor=new UserEventsCursor(this,cursor);
        list.setAdapter(eventsCursor);
    }
}
