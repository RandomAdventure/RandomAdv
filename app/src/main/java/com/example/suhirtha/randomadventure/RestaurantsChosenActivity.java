package com.example.suhirtha.randomadventure;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.suhirtha.randomadventure.models.DatabaseRestaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsChosenActivity extends AppCompatActivity {
    List<DatabaseRestaurant> restaurants;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_chosen);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerRestaurant);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //do this in the background, wrap in background
        //need this to insert data
        DatabaseHelper db = Room.databaseBuilder(getApplicationContext(), DatabaseHelper.class, "saved_restaurants")
                .allowMainThreadQueries() //TODO change this
                .build();

        restaurants = new ArrayList<DatabaseRestaurant>();
        restaurants = db.restaurantDao().getAllRestaurants();
        adapter = new RestaurantAdapter(restaurants);
        recyclerView.setAdapter(adapter);


        //USED TO INSERT INFO TO DATABASE
        //db.restaurantDao().insertAll(new DatabaseRestaurant("8dUaybEPHsZMgr1iKgqgMQ", "TestRestaurantName3"));
    }

}
