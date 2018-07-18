package com.example.suhirtha.randomadventure;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by togata on 7/16/18.
 */

public class ResultActivity extends AppCompatActivity {

    TextView mName;
    RatingBar mRating;
    TextView mAddress;
    YelpClient client;
    JSONObject restuarant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        mName = (TextView) findViewById(R.id.rsaName);
        mAddress = (TextView) findViewById(R.id.rsaAddress);
        mRating = (RatingBar) findViewById(R.id.rsaRating);

        client = new YelpClient();
        try {
            JSONObject restuarant = client.getBusinessInfo("w2TF4BebYFT2soafP28smw", this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyRestuarantUpdated(JSONObject restuarant){
        this.restuarant = restuarant;
        try {
            mName.setText(restuarant.getString("name"));
            mRating.setRating((float)restuarant.getDouble("rating"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
