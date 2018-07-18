package com.example.suhirtha.randomadventure;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
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

    private TextView mName;
    private RatingBar mRating;
    private TextView mAddress;
    private TextView mPhoneNumber;
    private YelpClient client;
    private String phoneNumber;
    private JSONObject restuarant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        mName = (TextView) findViewById(R.id.rsaName);
        mAddress = (TextView) findViewById(R.id.rsaAddress);
        mRating = (RatingBar) findViewById(R.id.rsaRating);
        mPhoneNumber = (TextView) findViewById(R.id.rsaPhoneNumber);
        mPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                if (phoneNumber.length()>11) {
                    callIntent.setData(Uri.parse("tel:" + phoneNumber.substring(1, 4) + phoneNumber.substring(6, 10) + phoneNumber.substring(11, phoneNumber.length())));
                }
                if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ResultActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                    return;
                }
                startActivity(callIntent);
            }
        });

        client = new YelpClient();
        try {
            JSONObject restuarant = client.getBusinessInfo("w2TF4BebYFT2soafP28smw", this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyRestuarantUpdated(JSONObject restuarant) {
        this.restuarant = restuarant;
        try {
            mName.setText(restuarant.getString("name"));
            mRating.setRating((float) restuarant.getDouble("rating"));
            JSONObject location = restuarant.getJSONObject("location");
            String address = location.getString("address1") + ", " + location.getString("city") + ", " + location.getString("state") + " " + location.getString("zip_code");
            mAddress.setText(address);
            phoneNumber = restuarant.getString("display_phone");
            mPhoneNumber.setText(phoneNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
