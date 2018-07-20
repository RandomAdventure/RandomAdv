package com.example.suhirtha.randomadventure;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Movie;
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

import com.example.suhirtha.randomadventure.models.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by togata on 7/16/18.
 */

public class ResultActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView mName;
    private RatingBar mRating;
    private TextView mAddress;
    private TextView mPhoneNumber;
    private CheckBox mDelivery;
    private CheckBox mReservation;
    private MapView mMap;
    private GoogleMap googleMap;
    private YelpClient client;
    private String phoneNumber;
    private double latitude;
    private double longitude;
    private JSONObject restuarant;
    private Restaurant passedRestaurant;

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
        mMap = (MapView) findViewById(R.id.rsaMap);
        mMap.onCreate(savedInstanceState);
        mMap.getMapAsync(this);

        client = new YelpClient();
        passedRestaurant = (Restaurant) Parcels.unwrap(getIntent().getParcelableExtra("test1"));
        try {
            JSONObject restuarant = client.getBusinessInfo(passedRestaurant.getId(), this);
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

            JSONObject coordinates = restuarant.getJSONObject("coordinates");
            latitude = (double) coordinates.get("latitude");
            longitude = (double) coordinates.get("longitude");
            LatLng latLng = new LatLng(latitude, longitude);
            updateLocation(latLng);

            JSONArray transactions = restuarant.getJSONArray("transactions");
            for (int i = 0; i < transactions.length(); i++) {
                if (transactions.get(i).equals("delivery")) {
                    mDelivery.setChecked(true);
                }
                if (transactions.get(i).equals("restaurant_reservation")) {
                    mReservation.setChecked(true);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void callRestuarant(View view) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        if (phoneNumber.length() > 11) {
            callIntent.setData(Uri.parse("tel:" + phoneNumber.substring(1, 4) + phoneNumber.substring(6, 9) + phoneNumber.substring(10, phoneNumber.length())));
        }
        if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ResultActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
            return;
        }
        startActivity(callIntent);
    }

    public void updateLocation(final LatLng latLng){
        this.runOnUiThread(new Runnable(){
            public void run(){
                googleMap.setMinZoomPreference(15);
                googleMap.addMarker(new MarkerOptions().position(latLng));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ResultActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        mMap.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMap.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMap.onStop();
    }
    @Override
    protected void onPause() {
        mMap.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mMap.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMap.onLowMemory();
    }



}
