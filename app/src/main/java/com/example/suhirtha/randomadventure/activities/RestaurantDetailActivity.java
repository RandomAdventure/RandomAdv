package com.example.suhirtha.randomadventure.activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.suhirtha.randomadventure.AppDatabase;
import com.example.suhirtha.randomadventure.R;
import com.example.suhirtha.randomadventure.models.DatabaseRestaurant;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by togata on 8/6/18.
 */

public class RestaurantDetailActivity extends AppCompatActivity {
    private TextView restaurantName;
    private RatingBar ratingBar;
    private TextView address;
    private EditText comment;
    DatabaseRestaurant passedRestaurant;
    int databasePosition;
    private List<DatabaseRestaurant> mRestaurants;
    private Button shareBtn;
    private Button backBtn;
    double ratingNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);


        restaurantName = findViewById(R.id.rdaName);
        ratingBar = findViewById(R.id.rdaRating);
        address = findViewById(R.id.rdaAddress);
        comment = findViewById(R.id.rdaInputText);
        shareBtn = findViewById(R.id.rdaShare);
        backBtn = findViewById(R.id.rdaGoBack);
        mRestaurants = new ArrayList<DatabaseRestaurant>();

        databasePosition = Parcels.unwrap(getIntent().getParcelableExtra("details"));
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "saved_restaurants")
                .allowMainThreadQueries()
                .build();
        passedRestaurant = db.restaurantDao().findRestaurantsById(databasePosition);


        restaurantName.setText(passedRestaurant.getRestaurantName());
        address.setText(passedRestaurant.getAddress());
        comment.setText(passedRestaurant.getComment());
        ratingBar.setRating(Float.parseFloat(passedRestaurant.getRating().toString())); //This is all Suhi

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ratingNumber = ratingBar.getRating();
            }
        });
    }

    public void onClickShare(View view) {
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "saved_restaurants")
                .allowMainThreadQueries()
                .build();
        db.restaurantDao().updateCritique(databasePosition, ratingNumber, comment.getText().toString());

        Intent i = new Intent(RestaurantDetailActivity.this, PreviousAdvActivity.class);
        startActivity(i);
    }

}
