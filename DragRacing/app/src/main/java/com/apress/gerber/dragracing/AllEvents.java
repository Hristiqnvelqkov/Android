package com.apress.gerber.dragracing;

import android.content.Intent;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.io.Serializable;

public class AllEvents extends AppCompatActivity {
    UserModel currentUser=new UserModel();
    UserDb mUserDb;
   @Override protected void onCreate(Bundle savedInstanceState) {
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
        mUserDb=new UserDb(this);
        mUserDb.open();
      if( mUserDb.checkSignIn(username,password)) {
          Intent intent = new Intent(this, Events.class);
          currentUser=mUserDb.getUser(username);
          intent.putExtra("currentUser",currentUser);
          startActivity(intent);
      }
        mUserDb.close();
    }
    public void signUp(View view){
        Intent intent=new Intent(this,SignUp.class);
        startActivity(intent);
    }
}
