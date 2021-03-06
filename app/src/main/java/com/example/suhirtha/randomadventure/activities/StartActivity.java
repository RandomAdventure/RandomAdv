package com.example.suhirtha.randomadventure.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

import com.example.suhirtha.randomadventure.R;

public class StartActivity extends AppCompatActivity {
    private ImageButton mStartSelection;
    private ImageButton mViewPrevious;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mStartSelection = findViewById(R.id.saStartRandomize);
        mViewPrevious = findViewById(R.id.saPreviousRestaurants);
        context = getApplicationContext();

        mStartSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, SelectionActivity.class);
                startActivity(i);
            }
        });

        mViewPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, PreviousAdvActivity.class);
                startActivity(i);
            }
        });

    }
}
