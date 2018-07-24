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
import android.widget.ImageView;
import android.widget.TextView;
import com.example.suhirtha.randomadventure.models.Restaurant;
import org.parceler.Parcels;
import java.util.ArrayList;
import java.util.Random;

public class RandomizeActivity extends AppCompatActivity {
    private ImageView spinWheel;
    private TextView[] res = new TextView[5];
    private Context context;
    private YelpClient client;
    private ArrayList<Restaurant> restaurants;
    private ArrayList<Restaurant> chosen;
    private Random random = new Random();
    private int generator;
    private int REQUEST_CODE_SELECTION = 10;
    private String[] selection = new String[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_randomize);
        spinWheel = findViewById(R.id.raWheel);
        context = getApplicationContext();
        restaurants = new ArrayList<>();
        chosen = new ArrayList<>();
        client = new YelpClient();
        res[0] = findViewById(R.id.raRestaurant);
        res[1] = findViewById(R.id.raRestaurant1);
        res[2] = findViewById(R.id.raRestaurant2);
        res[3] = findViewById(R.id.raRestaurant3);
        res[4] = findViewById(R.id.raRestaurant4);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //removes title

        restaurants = Parcels.unwrap(getIntent().getParcelableExtra("restaurants"));
        Log.d("RandomActivity", "Recieved arraylist of size: " + restaurants.size());

        spinWheel.setRotation(0);

        //-------------------------------------------------------------------------------------------

        for(int i = 0; i< selection.length; i++){ //assigns restaurant to fixed array
            //selection[i] = restaurants[random.nextInt(restaurants.size())];
            chosen.add(restaurants.get(random.nextInt(restaurants.size())));
            selection[i] = chosen.get(i).getName();
        }

        if(restaurants.size() >= selection.length){
            //if the length of the array of restaurants
            // is more than the length of the selection restaurants,
            // recheck for repeated restaurants
            for(int i = 0; i< selection.length; i++){ //replace pre-existing restaurants
                for (int k = i + 1; k < selection.length; k++) {
                    while (selection[i] == selection[k]){
                        chosen.remove(k);
                        chosen.add(k, restaurants.get(random.nextInt(restaurants.size())));
                        selection[k] = chosen.get(k).getName();
                    }
                }
                Log.d("RandomizeActivity", "Restaurant selection: " + selection[i]);
                res[i].setText(selection[i]);
            }
        }
        else{ //if length of restaurants is less than length selection, to avoid infinite loop, rechecks once for maximun efficency
            for(int i = 0; i< selection.length; i++){ //replace pre-existing restaurants
                for (int k = i + 1; k < selection.length; k++) {
                    if (selection[i] == selection[k]){
                        chosen.remove(k);
                        chosen.add(k, restaurants.get(random.nextInt(restaurants.size())));
                        selection[k] = chosen.get(k).getName();
                    }
                }
                Log.d("RandomizeActivity", "Restaurant selection: " + selection[i]);
                res[i].setText(selection[i]);
            }
        }

        //---------------------------------------------------------------------------------------------------------------------

    }

    public void onClickRandom(View view) {
        generator = random.nextInt(5); //randomly generates a number between 1 and 5
        int toDegrees =0 ;

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
            public void onAnimationStart(Animator animation) { spinWheel.setClickable(false); }

            @Override
            public void onAnimationRepeat(Animator animation) { spinWheel.setClickable(false); }

            @Override
            public void onAnimationEnd(Animator animation) {
                Intent i = new Intent(context, ResultActivity.class);
                i.putExtra("test1", Parcels.wrap(chosen.get(generator)));
                startActivity(i);
                spinWheel.setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animation) { spinWheel.setClickable(false); }
        });

        animatorSet.playTogether(rotateAnimation);
        animatorSet.start();
    }

    //toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
    }
}
