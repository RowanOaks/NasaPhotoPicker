package com.example.nasaphotopicker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);


        Bundle dataToPass = getIntent().getExtras();
        Log.i("In empty Activity:", "in empty activity");



        //Create a pass data to new fragment
        FrameLayout frame = (FrameLayout) findViewById(R.id.frame_layout);
        FavouriteImage frag = new FavouriteImage();
        frag.setArguments( dataToPass );
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,
                frag ).commit();
    }
}