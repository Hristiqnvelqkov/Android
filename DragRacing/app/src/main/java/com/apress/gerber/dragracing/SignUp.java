package com.apress.gerber.dragracing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SignUp extends AppCompatActivity {
    UserDb mUserDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void setUser(View view) {
        String name;
        String pass;
        EditText editText = (EditText) findViewById(R.id.setUsername);
        name = editText.getText().toString();
        EditText editText1 = (EditText) findViewById(R.id.setPass);
        pass=editText1.getText().toString();
        EditText editText2=(EditText) findViewById(R.id.retypePass);
        if(pass.equals(editText2.getText().toString())){
            mUserDb=new UserDb(this);
            mUserDb.open();
            mUserDb.createUser(name,pass);
            mUserDb.close();
            Intent intent = new Intent(this,AllEvents.class);
            startActivity(intent);
        }
    }
}
