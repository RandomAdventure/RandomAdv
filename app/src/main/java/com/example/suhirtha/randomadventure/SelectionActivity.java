package com.example.suhirtha.randomadventure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class SelectionActivity extends AppCompatActivity {
    final FragmentManager fragmentManager = getSupportFragmentManager();

    final Fragment accordionList = new AccordionFragment();
    private FragmentTransaction fragmentTransaction1;


//todo - visibility
    Button mSearch;
    Button mDone;
    SeekBar mRadius;
    TextView mRadiusDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction1.replace(R.id.saPlaceholderFragment, accordionList).commit();



        //initialize fields
        mSearch = findViewById(R.id.btnSearch);
        mDone = findViewById(R.id.btnDone);
        mRadius = findViewById(R.id.sbRadius2);
        mRadiusDisplay = findViewById(R.id.tvDisplayRadius);

        //onClickListener for 'Search' button - leads to Anna's randomizer activity
        //TODO - figure out how to pass on information entered by the user to client,
        //TODO - and then pass array of restaurants to Anna

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), RandomizeActivity.class);
                startActivity(i);
            }
        });

        //Temporary button that leads to Tatum's result activity
        //TODO - remove eventually
        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ResultActivity.class);
                startActivity(i);
            }
        });

        //TODO - temporary test code for seekBar, remove eventually
        // Initialize the textview with '0'.
        mRadiusDisplay.setText("Covered: " + mRadius.getProgress() + "/" + mRadius.getMax());

        mRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mRadiusDisplay.setText("Covered: " + progress + "/" + seekBar.getMax());
                Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
            }
        });
    }



}

