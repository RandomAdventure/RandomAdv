package com.example.suhirtha.randomadventure;

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
import android.widget.ImageView;

import java.util.Random;

public class RandomizeActivity extends AppCompatActivity {
    ImageView spinWheel;
    Context context;
    Animation clockwise, clockwise1, clockwise2 ,clockwise3, clockwise4;
    Random random = new Random();
    int generator;
    int REQUEST_CODE_SELECTION = 10;
    String[] restaurants= {"Pizzeria", "Italian", "Mexican", "Chuck E Cheese", "Fancy", "Barbeque", "French", "Brazilian", "Cuban", "Vegetarian"};
    String[] chosen = new String[5];
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        for(int i=0; i< chosen.length; i++){ //assigns restaurant to fixed array
            chosen[i] = restaurants[random.nextInt(restaurants.length)];
        }

        if(restaurants.length >= chosen.length){ //if the length of the array of restaurants
            //is more than the length of the chosen restaurants, recheck for repeated restaurants
            for(int i=0; i<chosen.length; i++){ //replace pre-existing restaurants
                for (int k = i + 1; k < chosen.length; k++) {
                    while (chosen[i] == chosen[k]){
                        chosen[k] = restaurants[random.nextInt(restaurants.length)];
                    }
                }
                Log.d("RandomizeActivity", "Restaurant chosen: " + chosen[i]);
            }
        }
        else{ //if length of restaurants is less than length chosen, to avoid infinite loop, rechecks once for maximun efficency
            for(int i=0; i<chosen.length; i++){ //replace pre-existing restaurants
                for (int k = i + 1; k < chosen.length; k++) {
                    if (chosen[i] == chosen[k]){
                        chosen[k] = restaurants[random.nextInt(restaurants.length)];
                    }
                }
                Log.d("RandomizeActivity", "Restaurant chosen: " + chosen[i]);
            }
        }

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
    public boolean onOptionsItemSelected(MenuItem item) { //empty on purpose
        return true;
    }

    public void onClickBack(View view) {
        Intent intent = new Intent(this, SelectionActivity.class);
        startActivityForResult(intent,REQUEST_CODE_SELECTION); //wrapping
        //shows post after composing on timeline, UNLIKE startActivity
    }
}
