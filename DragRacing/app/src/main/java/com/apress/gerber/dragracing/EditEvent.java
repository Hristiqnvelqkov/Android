package com.apress.gerber.dragracing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditEvent extends AppCompatActivity {
    EventDb mEventDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
    }
    public void updateEvent(View view){
        mEventDb=new EventDb(this);
        mEventDb.open();
        EditText editText=(EditText)findViewById(R.id.eventNewTitle);
        String newEventTitle=editText.getText().toString();
        EditText editText1=(EditText)findViewById(R.id.eventNewDesc);
        String newEventDesc=editText1.getText().toString();
        String event_id=getIntent().getStringExtra("event_id");
        int eventId=Integer.valueOf(event_id);
        mEventDb.updateEvent(eventId,newEventTitle,newEventDesc);
        mEventDb.close();
        Intent intent=new Intent(this,Events.class);
        startActivity(intent);
    }
}
