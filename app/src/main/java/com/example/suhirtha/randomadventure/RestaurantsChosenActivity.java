package com.example.suhirtha.randomadventure;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RestaurantsChosenActivity extends AppCompatActivity {
    DatabaseHelper db;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_chosen);
        context = getApplicationContext();
        db = new DatabaseHelper(context);
    }
}
