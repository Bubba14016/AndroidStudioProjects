package com.example.bubba.framelayout15api23;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    int contador=1;
    FrameLayout frame;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frame=(FrameLayout) findViewById(R.id.frame);
    }

    public void next(View view){
        img=(ImageView) frame.findViewWithTag(String.valueOf(contador));
        img.setVisibility(View.INVISIBLE);
        contador++;
        if (contador>3)contador=1;

        img=(ImageView) frame.findViewWithTag(String.valueOf(contador));
        img.setVisibility(View.VISIBLE);

    }
}
