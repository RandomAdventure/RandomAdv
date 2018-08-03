package com.example.suhirtha.randomadventure;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.suhirtha.randomadventure.models.DatabaseRestaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsChosenActivity extends AppCompatActivity {
    List<DatabaseRestaurant> restaurants;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    int REQUEST_CODE_SELECTION = 150;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_chosen);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.rcaToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //removes title

        recyclerView = (RecyclerView) findViewById(R.id.recyclerRestaurant);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //do this in the background, wrap in background
        //need this to insert data
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "saved_restaurants")
                .allowMainThreadQueries() //TODO change this
                .build();

        restaurants = new ArrayList<DatabaseRestaurant>();
        restaurants = db.restaurantDao().getAllRestaurants();
        adapter = new RestaurantAdapter(this, restaurants);
        recyclerView.setAdapter(adapter);


        //USED TO INSERT INFO TO DATABASE
        db.restaurantDao().insertAll(new DatabaseRestaurant("8dUaybEPHsZMgr1iKgqgMQ", "TestRestaurantName#"));
    }

    //toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_general, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //empty on purpose
        return true;
    }

    public void onClickBack(View view) {
        Intent intent = new Intent(this, StartActivity.class);
        startActivityForResult(intent,REQUEST_CODE_SELECTION); //wrapping
    }
    public void onClickDelete(View view) {
        //TODO delete everything in database
        Log.d("RChosenActivity", "Deleting database completely");
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "saved_restaurants")
                .allowMainThreadQueries() //TODO change this
                .build();
        db.restaurantDao().deleteAll();
        adapter.notifyDataSetChanged();
    }

}
