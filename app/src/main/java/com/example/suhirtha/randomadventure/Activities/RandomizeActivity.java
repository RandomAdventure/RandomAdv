package com.example.suhirtha.randomadventure.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import com.example.suhirtha.randomadventure.R;

import java.util.Random;

public class RandomizeActivity extends AppCompatActivity {
    Button rButton;
    ImageView spinWheel;
    Context context;
    Animation clockwise, clockwise1, clockwise2,clockwise3, clockwise4;
    Random random = new Random();
    int generator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomize);
        rButton = findViewById(R.id.raButton);
        spinWheel = findViewById(R.id.raImageView);
        context = getApplicationContext();
        clockwise = AnimationUtils.loadAnimation(context,R.anim.clockwise);
        clockwise1 = AnimationUtils.loadAnimation(context,R.anim.clockwise1);
        clockwise2 = AnimationUtils.loadAnimation(context,R.anim.clockwise2);
        clockwise3 = AnimationUtils.loadAnimation(context,R.anim.clockwise3);
        clockwise4 = AnimationUtils.loadAnimation(context,R.anim.clockwise4);
    }

    public void onClickRandom(View view) {
        generator = random.nextInt(5); //randomly generates a number between 1 and 5
        spinWheel.setRotation(0);
        switch (generator){
            case 0:
                spinWheel.startAnimation(clockwise); //1440 + 30
                Log.d("RandomizeActivity", generator + "Selected");
                break;
            case 1:
                spinWheel.startAnimation(clockwise1);//1440 + +100
                Log.d("RandomizeActivity", generator + "Selected");
            case 2:
                spinWheel.startAnimation(clockwise2); //1440 + 180
                Log.d("RandomizeActivity", generator + "Selected");
            break;
            case 3:
                spinWheel.startAnimation(clockwise3); //1440 + 270
                Log.d("RandomizeActivity", generator + "Selected");
            break;
            case 4:
                spinWheel.startAnimation(clockwise4); //1440 + 315
                Log.d("RandomizeActivity", generator + "Selected");
            break;
        }

    }
}
