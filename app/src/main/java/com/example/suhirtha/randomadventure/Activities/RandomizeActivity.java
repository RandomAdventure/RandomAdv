package com.example.suhirtha.randomadventure.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import com.example.suhirtha.randomadventure.R;
import com.example.suhirtha.randomadventure.ResultActivity;
import com.example.suhirtha.randomadventure.SelectionActivity;

import java.util.Random;

public class RandomizeActivity extends AppCompatActivity {
    Button rButton;
    ImageView spinWheel;
    Context context;
    Animation clockwise, clockwise1, clockwise2 ,clockwise3, clockwise4;
    Random random = new Random();
    int generator;
    int REQUEST_CODE_SELECTION = 10;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomize);
        spinWheel = findViewById(R.id.raImageView);
        context = getApplicationContext();
        clockwise = AnimationUtils.loadAnimation(context,R.anim.clockwise);
        clockwise1 = AnimationUtils.loadAnimation(context,R.anim.clockwise1);
        clockwise2 = AnimationUtils.loadAnimation(context,R.anim.clockwise2);
        clockwise3 = AnimationUtils.loadAnimation(context,R.anim.clockwise3);
        clockwise4 = AnimationUtils.loadAnimation(context,R.anim.clockwise4);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        setSupportActionBar(toolbar);
    }

    public void onClickRandom(View view) {
        generator = random.nextInt(5); //randomly generates a number between 1 and 5
        spinWheel.setRotation(0);
        switch (generator){
            case 4:
                spinWheel.startAnimation(clockwise); //1440 + 36 === 6 rotations
                Log.d("RandomizeActivity", generator + "Selected");
                clockwise.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {spinWheel.setEnabled(false);}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent i = new Intent(context, ResultActivity.class);
                        startActivity(i);
                        Log.d("RandomizeActivity", generator + "Animation ended");
                        spinWheel.setEnabled(true);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {spinWheel.setEnabled(true);}
                });
                break;
            case 3:
                spinWheel.startAnimation(clockwise1);//2160 + +108
                Log.d("RandomizeActivity", generator + "Selected");
                clockwise1.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {spinWheel.setEnabled(false);}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent i = new Intent(context, ResultActivity.class);
                        startActivity(i);
                        Log.d("RandomizeActivity", generator + "Animation ended");
                        spinWheel.setEnabled(true);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {spinWheel.setEnabled(true);}
                });
                break;
            case 2:
                spinWheel.startAnimation(clockwise2); //2160 + 180
                Log.d("RandomizeActivity", generator + "Selected");
                clockwise2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        spinWheel.setEnabled(false);
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent i = new Intent(context, ResultActivity.class);
                        startActivity(i);
                        Log.d("RandomizeActivity", generator + "Animation ended");
                        spinWheel.setEnabled(true);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {spinWheel.setEnabled(true);}
                });
                break;
            case 1:
                spinWheel.startAnimation(clockwise3); //2160+ 252
                Log.d("RandomizeActivity", generator + "Selected");
                clockwise3.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {spinWheel.setEnabled(false);}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent i = new Intent(context, ResultActivity.class);
                        startActivity(i);
                        Log.d("RandomizeActivity", generator + "Animation ended");
                        spinWheel.setEnabled(true);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {spinWheel.setEnabled(true);}
                });
                break;
            case 0:
                spinWheel.startAnimation(clockwise4); //2160 + 324
                Log.d("RandomizeActivity", generator + "Selected");
                clockwise4.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {spinWheel.setEnabled(false);}

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent i = new Intent(context, ResultActivity.class);
                        startActivity(i);
                        Log.d("RandomizeActivity", generator + "Animation ended");
                        spinWheel.setEnabled(true);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {spinWheel.setEnabled(true);}
                });
                break;
        }
    }

    //toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_random, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.mrBack:
                Intent intent = new Intent(this, SelectionActivity.class);
                startActivityForResult(intent,REQUEST_CODE_SELECTION); //wrapping
                //shows post after composing on timeline, UNLIKE startActivity
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
