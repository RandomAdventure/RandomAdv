package com.example.suhirtha.randomadventure;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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
import android.widget.TextView;

import com.example.suhirtha.randomadventure.models.Restaurant;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Random;

public class RandomizeActivity extends AppCompatActivity {
    ImageView spinWheel;
    TextView[] res = new TextView[5];
    Context context;
    YelpClient client;
    ArrayList<Restaurant> testRestaurants;
    ArrayList<Restaurant> testChosen;
    Animation clockwise;
    Random random = new Random();
    int generator;
    int REQUEST_CODE_SELECTION = 10;
    String[] chosen = new String[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomize);
        spinWheel = findViewById(R.id.raWheel);
        context = getApplicationContext();
        testRestaurants = new ArrayList<>();
        testChosen = new ArrayList<>();
        client = new YelpClient();
        clockwise = AnimationUtils.loadAnimation(context,R.anim.clockwise);
        res[0] = findViewById(R.id.raRestaurant);
        res[1] = findViewById(R.id.raRestaurant1);
        res[2] = findViewById(R.id.raRestaurant2);
        res[3] = findViewById(R.id.raRestaurant3);
        res[4] = findViewById(R.id.raRestaurant4);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //removes title

        testRestaurants = Parcels.unwrap(getIntent().getParcelableExtra("restaurants"));
        Log.d("RandomActivity", "Recieved arraylist of size: " + testRestaurants.size());

        //-------------------------------------------------------------------------------------------

        for(int i=0; i< chosen.length; i++){ //assigns restaurant to fixed array
            //chosen[i] = restaurants[random.nextInt(testRestaurants.size())];
            testChosen.add(testRestaurants.get(random.nextInt(testRestaurants.size())));
            chosen[i] = testChosen.get(i).getName();
        }

        if(testRestaurants.size() >= chosen.length){
            //if the length of the array of restaurants
            // is more than the length of the chosen restaurants,
            // recheck for repeated restaurants
            for(int i=0; i<chosen.length; i++){ //replace pre-existing restaurants
                for (int k = i + 1; k < chosen.length; k++) {
                    while (chosen[i] == chosen[k]){
                        testChosen.remove(k);
                        testChosen.add(k, testRestaurants.get(random.nextInt(testRestaurants.size())));
                        chosen[k] = testChosen.get(k).getName();
                    }
                }
                Log.d("RandomizeActivity", "Restaurant chosen: " + chosen[i]);
                res[i].setText(chosen[i]);
            }
        }
        else{ //if length of restaurants is less than length chosen, to avoid infinite loop, rechecks once for maximun efficency
            for(int i=0; i<chosen.length; i++){ //replace pre-existing restaurants
                for (int k = i + 1; k < chosen.length; k++) {
                    if (chosen[i] == chosen[k]){
                        testChosen.remove(k);
                        testChosen.add(k, testRestaurants.get(random.nextInt(testRestaurants.size())));
                        chosen[k] = testChosen.get(k).getName();
                    }
                }
                Log.d("RandomizeActivity", "Restaurant chosen: " + chosen[i]);
                res[i].setText(chosen[i]);
            }
        }

        //---------------------------------------------------------------------------------------------------------------------

    }

    public void onClickRandom(View view) {
        generator = random.nextInt(5); //randomly generates a number between 1 and 5
        spinWheel.setRotation(0);
        int toDegrees =0 ;

        generator = 3;
        switch (generator){
            case 4: //2160 + 36 === 6 rotations
                toDegrees = 2196;
                Log.d("RandomizeActivity", generator + "Selected");
                break;
            case 3://2160 + +108
                toDegrees = 2268;
                Log.d("RandomizeActivity", generator + "Selected");
                break;
            case 2://2160 + 180
                toDegrees = 2340;
                Log.d("RandomizeActivity", generator + "Selected");
                break;
            case 1://2160+ 252
                toDegrees = 2411;
                Log.d("RandomizeActivity", generator + "Selected");
                break;
            case 0: //2160 + 324
                toDegrees = 2484;
                Log.d("RandomizeActivity", generator + "Selected");
                break;
        }

        ObjectAnimator rotateAnimation = ObjectAnimator.ofFloat(spinWheel, "rotation", 0, toDegrees);
        rotateAnimation.setDuration(10000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                // ...
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // ...
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Intent i = new Intent(context, ResultActivity.class);
                i.putExtra("test1", Parcels.wrap(testChosen.get(generator)));
                startActivity(i);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // ...
            }
        });

        animatorSet.playTogether(rotateAnimation);
        animatorSet.start();
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
