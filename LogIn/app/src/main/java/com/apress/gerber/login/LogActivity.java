package com.apress.gerber.login;

import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

public class LogActivity extends AppCompatActivity implements BaseFragment.OnFragmentInteractionListener {
    private  static FragmentManager manager;
    static final String CITY_EXTRA = "city";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        manager = getSupportFragmentManager();
        if (manager.findFragmentByTag("location") == null) {
            if (manager.findFragmentByTag("log") == null) {
                Fragment logForm = new LogFormFragment();
                commitFragment(false, "log", logForm);
            }
        }
    }
    void commitFragment(boolean addToBackStack, String fragmentTag, Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        if (addToBackStack) {
            transaction.addToBackStack(fragmentTag);
        }
        if (fragment instanceof LogFormFragment) {
            transaction.add(R.id.activity_log, fragment, fragmentTag);
        } else if (fragment instanceof LocationFragment) {
            transaction.replace(R.id.activity_log, fragment, fragmentTag);
        }
        transaction.commit();
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
