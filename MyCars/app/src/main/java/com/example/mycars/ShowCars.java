package com.example.mycars;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

public class ShowCars extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cars);
        Intent intent=getIntent();
        String mark=intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        TextView textView=new TextView(this);
        textView.setTextSize(40);
        textView.setText(mark);
        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_show_cars);
        layout.addView(textView);
    }
}
