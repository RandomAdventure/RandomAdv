package com.example.suhirtha.randomadventure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.suhirtha.randomadventure.Activities.RandomizeActivity;

public class SelectionActivity extends AppCompatActivity {

    Button mSearch;
    Button mDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        mSearch = findViewById(R.id.btnSearch);
        mDone = findViewById(R.id.btnDone);

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), RandomizeActivity.class);
                startActivity(i);
            }
        });

        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ResultActivity.class);
                startActivity(i);
            }
        });


    }
}
