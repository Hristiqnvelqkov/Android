package com.apress.gerber.dragracing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AllEvents extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_events);
    }
    public void SignIn(View view){
        String username;
        EditText getUsername=(EditText) findViewById(R.id.username);
        username=getUsername.getText().toString();
        String password;
        EditText getPass=(EditText) findViewById(R.id.password);
        password=getPass.getText().toString();
        Intent intent=new Intent(this,Events.class);
        startActivity(intent);
    }
    public void signUp(View view){
        Intent intent=new Intent(this,SignUp.class);
        startActivity(intent);
    }
}
