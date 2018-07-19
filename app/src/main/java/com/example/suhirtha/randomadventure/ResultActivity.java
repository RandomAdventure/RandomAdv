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
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
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
    private CheckBox mDelivery;
    private CheckBox mReservation;
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
        mReservation = (CheckBox) findViewById(R.id.rsaReservation);
        mReservation.setClickable(false);
        mDelivery = (CheckBox) findViewById(R.id.rsaDelivery);
        mDelivery.setClickable(false);

        client = new YelpClient();
        try {
            JSONObject restuarant = client.getBusinessInfo("kg_DZXn2PothfiFmR4QWgA", this);

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
            //String[] transactions = (String[]) restuarant.get("transactions");
            JSONArray transactions = restuarant.getJSONArray("transactions");
            for (int i=0; i<transactions.length(); i++){
                if (transactions.get(i).equals("delivery")){
                    mDelivery.setChecked(true);
                }
                if (transactions.get(i).equals("restaurant_reservation")){
                    mReservation.setChecked(true);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void callRestuarant(View view){
        Toast.makeText(view.getContext(), "clicked", Toast.LENGTH_SHORT);
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        if (phoneNumber.length()>11) {
            callIntent.setData(Uri.parse("tel:" + phoneNumber.substring(1, 4) + phoneNumber.substring(6, 9) + phoneNumber.substring(10, phoneNumber.length())));
        }
        if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ResultActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            return;
        }
        startActivity(callIntent);
    }



}
