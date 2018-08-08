package com.example.suhirtha.randomadventure.activities;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        restaurantName = findViewById(R.id.rdaName);
        ratingBar = findViewById(R.id.rdaRating);
        address = findViewById(R.id.rdaAddress);
        comment = findViewById(R.id.rdaInputText);
        mRestaurants = new ArrayList<DatabaseRestaurant>();

        databasePosition = Parcels.unwrap(getIntent().getParcelableExtra("details"));
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "saved_restaurants")
                .allowMainThreadQueries()
                .build();
        passedRestaurant = db.restaurantDao().findRestaurantsById(databasePosition);

        restaurantName.setText(passedRestaurant.getRestaurantName());
        address.setText(passedRestaurant.getAddress());
        comment.setText(passedRestaurant.getComment());
    }
}
