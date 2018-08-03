package com.example.suhirtha.randomadventure;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.suhirtha.randomadventure.models.DatabaseRestaurant;

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
        adapter = new RestaurantAdapter(restaurants);
        recyclerView.setAdapter(adapter);

        DatabaseHelper db = Room.databaseBuilder(getApplicationContext(), DatabaseHelper.class, "saved_restaurants")
                .build();


        restaurants = db.restaurantDao().getAllRestaurants();

        //restaurants = new ArrayList<DatabaseRestaurant>();
        // TO SHOW ALL DATABASES
        showRestaurants();
    }

    private void showRestaurants() {

        for (int i = 0; i < 5; i++) {
            //DatabaseRestaurant restaurant = new DatabaseRestaurant("8dUaybEPHsZMgr1iKgqgMQ", "Sotto Mare Oysteria #" + i);
            //restaurants.add(restaurant);
        }
    }

}
