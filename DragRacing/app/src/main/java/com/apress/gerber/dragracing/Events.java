package com.apress.gerber.dragracing;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import static android.R.attr.button;

public class Events extends AppCompatActivity {
    EventDb mEventDb;
    MyCurosr myCurosr;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        mEventDb=new EventDb(this);
        Cursor events;
        EditText editText=(EditText) findViewById(R.id.eventTitle);
        editText.setVisibility(View.GONE);
        EditText editText1=(EditText) findViewById(R.id.eventDesc);
        editText1.setVisibility(View.GONE);
        Button event=(Button) findViewById(R.id.submitEvent);
        event.setVisibility(View.GONE);
        mEventDb.open();
        events=mEventDb.getAllEvents();
        listView=(ListView)findViewById(R.id.eventList);
        myCurosr=new MyCurosr(this,events);
        listView.setAdapter(myCurosr);
        mEventDb.close();

    }
    public void createEvent(View view){
        EditText editText=(EditText) findViewById(R.id.eventTitle);
        EditText editText1=(EditText) findViewById(R.id.eventDesc);
        Button button=(Button) findViewById(R.id.createEvent);
        button.setVisibility(view.GONE);
        editText.setVisibility(view.VISIBLE);
        editText1.setVisibility(view.VISIBLE);
        Button event=(Button) findViewById(R.id.submitEvent);
        event.setVisibility(view.VISIBLE);
    }
    public void setEvent(View view){
        EditText editText=(EditText) findViewById(R.id.eventTitle);
        String title=editText.getText().toString();
        EditText editText1=(EditText) findViewById(R.id.eventDesc);
        String desc=editText.getText().toString();
        mEventDb.open();
        mEventDb.createEvent(title,desc);
        Cursor cursor=mEventDb.getAllEvents();
        listView=(ListView) findViewById(R.id.eventList);
        myCurosr=new MyCurosr(this,cursor);
        listView.setAdapter(myCurosr);
        mEventDb.close();
        editText.setVisibility(view.GONE);
        editText1.setVisibility(View.GONE);
        Button event=(Button) findViewById(R.id.submitEvent);
        event.setVisibility(View.GONE);
        Button button=(Button) findViewById(R.id.createEvent);
        button.setVisibility(View.VISIBLE);


    }
}
