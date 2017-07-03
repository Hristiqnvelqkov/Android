package com.apress.gerber.login;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * <p>
 * create an instance of this fragment.
 */
public class LogFormFragment extends BaseFragment implements View.OnClickListener {
    View myView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fragment_log_form, container, false);
        Button button = (Button) myView.findViewById(R.id.log_in);
        button.setOnClickListener(this);
        return myView;
    }
    boolean validation(EditText email, TextInputLayout password) {
        boolean status = false;
        if (!email.getText().toString().equals("")) {
            status = Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches();
            if (!status) {
                email.setError("Email is not valid");
            } else {
                if (password.getEditText() != null) {
                    if (!(password.getEditText().length() == 0)) {
                        if (password.getEditText().length() < 5) {
                            status = false;
                            password.setError("Password is not valid");
                        }
                    } else {
                        password.setError("Enter password");
                        status = false;
                    }
                }
            }
        } else {
            status = false;
            email.setError("Enter email");
        }
        return status;
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.log_in){
            LogActivity logActivity = new LogActivity();
            EditText email = (EditText) myView.findViewById(R.id.email_field);
            TextInputLayout password = (TextInputLayout) myView.findViewById(R.id.password);
            Fragment location = new LocationFragment();
            if (validation(email, password)) {
                logActivity.commitFragment(true, "location", location);
                password.setErrorEnabled(false);
            }
        }
    }
}
