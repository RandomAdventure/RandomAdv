package com.example.suhirtha.randomadventure;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by togata on 7/16/18.
 */

public class ResultActivity  extends AppCompatActivity {

    TextView mTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        mTest = findViewById(R.id.tvTest);
        setContentView(R.layout.activity_result);
        try {
            YelpClient hello = new YelpClient(this);
            mTest.setText("hi");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
