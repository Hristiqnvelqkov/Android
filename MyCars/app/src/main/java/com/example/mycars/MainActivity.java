package com.example.mycars;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE="key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void newCar(View view){
        Intent intent=new Intent(this,ShowCars.class);
        EditText text1=(EditText) findViewById(R.id.carMark);
        String mark=text1.getText().toString();
        intent.putExtra(EXTRA_MESSAGE,mark);
        startActivity(intent);
    }
}
