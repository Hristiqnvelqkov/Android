package com.apress.gerber.dragracing;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.provider.Settings;
import android.support.annotation.MainThread;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import static android.R.attr.button;
import static android.R.attr.editorExtras;
import static android.R.attr.start;
import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

public class Events extends AppCompatActivity {
    EventDb mEventDb;
    MyCurosr myCurosr;
    ListView listView;
    UserModel currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        mEventDb=new EventDb(this);
        Cursor events;
        final EditText editText=(EditText) findViewById(R.id.eventTitle);
        editText.setVisibility(View.GONE);
        final EditText editText1=(EditText) findViewById(R.id.eventDesc);
        editText1.setVisibility(View.GONE);
        Button event=(Button) findViewById(R.id.submitEvent);
        event.setVisibility(View.GONE);
        mEventDb.open();
        currentUser=getIntent().getParcelableExtra("currentUser");
        events=mEventDb.getAllEvents();
        listView=(ListView)findViewById(R.id.eventList);
        myCurosr=new MyCurosr(this,events);
        listView.setAdapter(myCurosr);
        mEventDb.close();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               final int eventId= (int) parent.getItemIdAtPosition(position);
                mEventDb.open();
                final AlertDialog alert=new AlertDialog.Builder(Events.this).create();
                alert.setButton(BUTTON_POSITIVE,"Edit Event",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Events.this,EditEvent.class);
                        intent.putExtra("currentUser",currentUser);
                        intent.putExtra("event_id",eventId+"");
                        startActivity(intent);
                        mEventDb.close();
                        alert.cancel();
                    }
                });
                alert.setButton(BUTTON_NEGATIVE, "Delete Event",
                        new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mEventDb.deleteEvent(eventId);
                        ListView newList=(ListView) findViewById(R.id.eventList);
                        Cursor cursor=mEventDb.getAllEvents();
                        MyCurosr events=new MyCurosr(Events.this,cursor);
                        newList.setAdapter(events);
                        mEventDb.close();
                        alert.cancel();
                    }
                });
                alert.show();
            }
        });
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
        mEventDb.createEvent(title,desc,currentUser);
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
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.myProfile){
            Intent intent=new Intent(this,MyProfile.class);
            intent.putExtra("currentUser",currentUser);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
