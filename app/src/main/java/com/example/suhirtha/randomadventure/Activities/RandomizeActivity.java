package com.example.suhirtha.randomadventure.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.suhirtha.randomadventure.R;

public class RandomizeActivity extends AppCompatActivity {
    Button rButton;
    ImageView spinWheel;
    //Animation clockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.clockwise);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomize);
        rButton = findViewById(R.id.raButton);
        spinWheel = findViewById(R.id.raImageView);

    }

    public void onClickRandom(View view) {
        //clockwise.setTarget(spinWheel);
        //clockwise.setDuration(3000);
        //spinWheel.startAnimation(clockwise);

    }
}
