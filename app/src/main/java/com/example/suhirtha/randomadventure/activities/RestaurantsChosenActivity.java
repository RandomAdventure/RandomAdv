package com.example.suhirtha.randomadventure.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.suhirtha.randomadventure.AppDatabase;
import com.example.suhirtha.randomadventure.R;
import com.example.suhirtha.randomadventure.adapters.RestaurantAdapter;
import com.example.suhirtha.randomadventure.models.DatabaseRestaurant;
import com.example.suhirtha.randomadventure.viewModels.RestaurantsChosenViewModel;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsChosenActivity extends AppCompatActivity implements View.OnLongClickListener {
    private  List<DatabaseRestaurant> restaurants;
    RecyclerView recyclerView;
    RestaurantAdapter adapter;
    int REQUEST_CODE_SELECTION = 150;
    RestaurantsChosenViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_chosen);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.rcaToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false); //removes title

        restaurants = new ArrayList<DatabaseRestaurant>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerRestaurant);
        adapter = new RestaurantAdapter(this, restaurants, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(RestaurantsChosenViewModel.class);

        viewModel.getmRestaurants().observe(RestaurantsChosenActivity.this, new Observer<List<DatabaseRestaurant>>() {
            @Override
            public void onChanged(@Nullable List<DatabaseRestaurant> restaurants) {
                adapter.addItems(restaurants);
            }
        });
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

//        viewModel.deleteAllRestaurants();
        //TODO delete everything in database
        Log.d("RChosenActivity", "Deleting database completely");
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "saved_restaurants")
                .allowMainThreadQueries()
                .build();
        db.restaurantDao().deleteAll();
        Toast.makeText(this, "Database deleted!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onLongClick(View view) {
        DatabaseRestaurant databaseRestaurant = (DatabaseRestaurant) view.getTag();
        viewModel.deleteRestaurant(databaseRestaurant);
        return true;
    }
}
