package com.apress.gerber.camera;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import layout.ShowImage;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void makePicture(View view){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,REQUEST_IMAGE);
    }
    @Override protected void onActivityResult (int requestCode,int resultCode,Intent data){
        if(requestCode==REQUEST_IMAGE){
            if (resultCode==RESULT_OK){
                Bundle extras=data.getExtras();
                Bitmap image=(Bitmap) extras.get("data");
                FragmentManager manager=getSupportFragmentManager();
                FragmentTransaction transaction=manager.beginTransaction();
                transaction.add(R.id.activity_main,new ShowImage(),"test");
                transaction.commitAllowingStateLoss();
                ImageView imageView=(ImageView) findViewById(R.id.imageView);
                imageView.setImageBitmap(image);
            }
        }
    }
}
