package com.apress.gerber.login;

import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LogActivity extends AppCompatActivity {
    private FragmentManager manager;
    static final String CITY_EXTRA="city";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        manager = getSupportFragmentManager();
        if(manager.findFragmentByTag("location")==null) {
            if(manager.findFragmentByTag("log")==null) {
                Fragment logForm = new LogFormFragment();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.add(R.id.activity_log, logForm, "log");
                transaction.commit();
            }
        }
    }
    boolean validation(EditText email, TextInputLayout password){
        boolean status = false ;
        if (!email.getText().toString().equals("")){
            status = Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches();
            if(!status){
                email.setError("Email is not valid");
            }else{
                if(password.getEditText()!=null) {
                    if (!(password.getEditText().length()==0)) {
                        if (password.getEditText().length() < 5) {
                            status = false;
                            password.setError("Password is not valid");
                        }
                    } else {
                        password.setError("Enter password");
                        status=false;
                    }
                }
            }
        }else{
            status=false;
            email.setError("Enter email");
        }
        return status;
    }

    public void logIn(View view) {
        EditText email = (EditText) findViewById(R.id.email_field);
        TextInputLayout password = (TextInputLayout) findViewById(R.id.password);
        Fragment location = new Location();
        if(validation(email,password)) {
            FragmentTransaction deleteForm = manager.beginTransaction();
            deleteForm.addToBackStack("log");
            deleteForm.replace(R.id.activity_log, location, "location");
            deleteForm.commit();
            password.setErrorEnabled(false);
        }
    }
}
