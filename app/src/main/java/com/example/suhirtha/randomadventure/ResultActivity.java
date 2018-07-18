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
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by togata on 7/16/18.
 */

public class ResultActivity extends AppCompatActivity {

    TextView mTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        com.example.suhirtha.randomadventure.Location location = new com.example.suhirtha.randomadventure.Location(this.getApplicationContext(), this);
        Log.d("location", "class Latitude: "+location.getLatitude());
        Log.d("location", "class Longitude: "+location.getLongitude());
    }

}
