package com.example.suhirtha.randomadventure.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.suhirtha.randomadventure.R;

public class RandomizeActivity extends AppCompatActivity {
    Button rButton;
    ImageView spinWheel;
    Context context;
    Animation clockwise;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomize);
        rButton = findViewById(R.id.raButton);
        spinWheel = findViewById(R.id.raImageView);
        context = getApplicationContext();
        clockwise = AnimationUtils.loadAnimation(context,R.anim.clockwise);

    }

    public void onClickRandom(View view) {
        clockwise.setDuration(3000);
        spinWheel.startAnimation(clockwise);

    }
}
