package com.apress.gerber.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaCodec;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class Log extends AppCompatActivity {
    private FragmentManager manager;
    static final String CITY_EXTRA="city";
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        manager = getSupportFragmentManager();
        if(manager.findFragmentByTag("location")==null) {
            if(manager.findFragmentByTag("log")==null) {
                Fragment logForm = new LogForm();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.activity_log, logForm, "log");
                transaction.commit();
            }
        }
    }
    boolean validatePassword(TextInputLayout password){
        boolean status = false;
        if(password.getEditText()!=null){
            status=true;
            if(password.getEditText().length()<5){
                status=false;
            }
        }
        return status;
    }
    boolean validateEmail(EditText email) {
        boolean matches=false;
        if (email.getText() != null){
            System.out.println("YEEEEEEE");
            System.out.println(email.getText().toString());
            matches = Pattern.matches(EMAIL_PATTERN, email.getText().toString());
            System.out.println(email.getText());
        }
        return matches;
    }
    public void logIn(View view) {
        EditText email = (EditText) findViewById(R.id.email_field);
        TextInputLayout password = (TextInputLayout) findViewById(R.id.password);
        Fragment location = new Location();
        boolean valid_password = validatePassword(password);
        boolean valid_email = validateEmail(email);
        if(valid_email) {
            if (valid_password) {
                FragmentTransaction deleteForm = manager.beginTransaction();
                deleteForm.addToBackStack("log");
                deleteForm.remove(manager.findFragmentByTag("log"));
                deleteForm.add(R.id.activity_log,location,"location");
                deleteForm.commit();
            }else{
                Toast.makeText(this,"password is not valid",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"email is not valid",Toast.LENGTH_SHORT).show();
        }
    }

}
