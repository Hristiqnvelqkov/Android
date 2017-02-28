package com.apress.gerber.stolencars;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {
    private UserDb mUserDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }
    public void makeSignUp(View view){
        Intent intent=new Intent(this,CheckPart.class);
        EditText editText = (EditText) findViewById(R.id.email);
        String email=editText.getText().toString();
        EditText editText1= (EditText) findViewById(R.id.pass);
        String pass=editText1.getText().toString();
        UserDb mUserDb=new UserDb(this);
        mUserDb.open();
        mUserDb.createUser(email,pass);
        startActivity(intent);

    }
}
