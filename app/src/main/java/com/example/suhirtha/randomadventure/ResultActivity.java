package com.example.suhirtha.randomadventure;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Info;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.example.suhirtha.randomadventure.models.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

/**
 * Created by togata on 7/16/18.
 */

public class ResultActivity extends AppCompatActivity implements OnMapReadyCallback, RestaurantListener, LocationListenerRandomAdv {

    private Context context;
    public ResultActivity activity;
    private TextView mName;
    private RatingBar mRating;
    private TextView mAddress;
    private TextView mPhoneNumber;
    private CheckBox mDelivery;
    private CheckBox mReservation;
    private MapView mMap;
    private GoogleMap googleMap;
    private TextView mDistance;
    private TextView mDuration;
    private ImageButton mWalking;
    private ImageButton mDriving;
    private ProgressBar mProgressBar;
    private YelpClient client;
    private String phoneNumber;
    private double latitude;
    private double longitude;
    public JSONObject restuarant;
    private Restaurant passedRestaurant;
    public LatLng destination;
    private LatLng origin;
    private Direction directions;

    private ResultViewModel model;

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
        mDistance = (TextView) findViewById(R.id.rsaDistance);
        mDuration = (TextView) findViewById(R.id.rsaDuration);
        mWalking = (ImageButton) findViewById(R.id.rsaWalking);
        mDriving = (ImageButton) findViewById(R.id.rsaDriving);
        mProgressBar = (ProgressBar) findViewById(R.id.rsaProgressBar);

        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.VISIBLE);
        mName.setVisibility(View.INVISIBLE);
        mRating.setVisibility(View.INVISIBLE);
        mAddress.setVisibility(View.INVISIBLE);
        mReservation.setVisibility(View.INVISIBLE);
        mDelivery.setVisibility(View.INVISIBLE);
        mPhoneNumber.setVisibility(View.INVISIBLE);
        mMap.setVisibility(View.INVISIBLE);
        mDistance.setVisibility(View.INVISIBLE);
        mDuration.setVisibility(View.INVISIBLE);
        mWalking.setVisibility(View.INVISIBLE);
        mDriving.setVisibility(View.INVISIBLE);

        //client = new YelpClient(this);
        passedRestaurant = (Restaurant) Parcels.unwrap(getIntent().getParcelableExtra("test1"));
        /*try {
            JSONObject restuarant = client.getBusinessInfo(passedRestaurant.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        context = this.getApplicationContext();
        activity = this;

        Location currentLocation = new com.example.suhirtha.randomadventure.Location(context, this, this);
        origin = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());

        model= ViewModelProviders.of(this).get(ResultViewModel.class);
    }

    @SuppressLint("ResourceType")
    @Override
    public void restaurantInfo(JSONObject restuarant) {
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
            this.destination = latLng;
            walkingDirections((View)findViewById(R.layout.activity_result));

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


    public void restaurant() {
        final Observer<JSONObject> restuarantInfoObserver = new Observer<JSONObject>() {
            @SuppressLint("ResourceType")
            @Override
            public void onChanged(@Nullable JSONObject restuarant) {
                activity.restuarant = restuarant;
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
                    activity.destination = latLng;
                    walkingDirections((View)findViewById(R.layout.activity_result));

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
        };
        model.getBusinessInfo(getResources().getString(R.string.yelp_api_key), passedRestaurant.getId()).observe(this, restuarantInfoObserver);

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

    @SuppressLint("ResourceType")
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callRestuarant((View) findViewById(R.layout.activity_result));
                }
                return;
            }
        }
    }

    public void walkingDirections(View v){
        this.destination = destination;
        final Observer<Direction> walkingDirectionObserver = new Observer<Direction>() {
            @Override
            public void onChanged(@Nullable final Direction direction) {
                activity.runOnUiThread(new Runnable(){
                    public void run(){
                        mProgressBar.setVisibility(View.INVISIBLE);
                        mName.setVisibility(View.VISIBLE);
                        mRating.setVisibility(View.VISIBLE);
                        mAddress.setVisibility(View.VISIBLE);
                        mReservation.setVisibility(View.VISIBLE);
                        mDelivery.setVisibility(View.VISIBLE);
                        mPhoneNumber.setVisibility(View.VISIBLE);
                        mDistance.setVisibility(View.VISIBLE);
                        mDuration.setVisibility(View.VISIBLE);
                        googleMap.clear();
                        mMap.setVisibility(View.VISIBLE);
                        mWalking.setVisibility(View.VISIBLE);
                        mDriving.setVisibility(View.VISIBLE);
                        mWalking.setBackgroundColor(getResources().getColor(R.color.clearPurple));
                        mDriving.setBackgroundColor(getResources().getColor(R.color.clear));
                        Route route = direction.getRouteList().get(0);
                        Leg leg = route.getLegList().get(0);
                        ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();
                        PolylineOptions polylineOptions = DirectionConverter.createPolyline(context, directionPositionList, 5, getResources().getColor(R.color.darkPurple));
                        googleMap.addPolyline(polylineOptions);
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
                        googleMap.addMarker(new MarkerOptions().position(origin));
                        googleMap.addMarker(new MarkerOptions().position(destination));
                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        builder.include(origin);
                        builder.include(destination);
                        LatLngBounds bounds = builder.build();
                        int padding = 60;
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                        googleMap.animateCamera(cameraUpdate);

                        Info distanceInfo = leg.getDistance();
                        Info durationInfo = leg.getDuration();
                        mDistance.setText(distanceInfo.getText());
                        mDuration.setText(durationInfo.getText());

                    }
                });

            }
        };
        model.getWalkingDirections(getResources().getString(R.string.google_maps_api_key), origin, destination).observe(this, walkingDirectionObserver);
    }

    public void drivingDirections(View v){
        final Observer<Direction> drivingDirectionObserver = new Observer<Direction>() {
            @Override
            public void onChanged(@Nullable final Direction direction) {
                activity.runOnUiThread(new Runnable(){
                    public void run(){
                        mProgressBar.setVisibility(View.INVISIBLE);
                        mName.setVisibility(View.VISIBLE);
                        mRating.setVisibility(View.VISIBLE);
                        mAddress.setVisibility(View.VISIBLE);
                        mReservation.setVisibility(View.VISIBLE);
                        mDelivery.setVisibility(View.VISIBLE);
                        mPhoneNumber.setVisibility(View.VISIBLE);
                        mDistance.setVisibility(View.VISIBLE);
                        mDuration.setVisibility(View.VISIBLE);
                        googleMap.clear();
                        mMap.setVisibility(View.VISIBLE);
                        mWalking.setVisibility(View.VISIBLE);
                        mDriving.setVisibility(View.VISIBLE);
                        mWalking.setBackgroundColor(getResources().getColor(R.color.clear));
                        mDriving.setBackgroundColor(getResources().getColor(R.color.clearPurple));
                        Route route = direction.getRouteList().get(0);
                        Leg leg = route.getLegList().get(0);
                        ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();
                        PolylineOptions polylineOptions = DirectionConverter.createPolyline(context, directionPositionList, 5, getResources().getColor(R.color.darkPurple));
                        googleMap.addPolyline(polylineOptions);
                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
                        googleMap.addMarker(new MarkerOptions().position(origin));
                        googleMap.addMarker(new MarkerOptions().position(destination));
                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        builder.include(origin);
                        builder.include(destination);
                        LatLngBounds bounds = builder.build();
                        int padding = 60;
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                        googleMap.animateCamera(cameraUpdate);

                        Info distanceInfo = leg.getDistance();
                        Info durationInfo = leg.getDuration();
                        mDistance.setText(distanceInfo.getText());
                        mDuration.setText(durationInfo.getText());

                    }
                });

            }
        };
        model.getDrivingDirections(getResources().getString(R.string.google_maps_api_key), origin, destination).observe(this, drivingDirectionObserver);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ResultActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        restaurant();
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

    @SuppressLint("ResourceType")
    @Override
    public void locationChange(LatLng newLocation){
        origin = newLocation;
        walkingDirections((View)findViewById(R.layout.activity_result));
    }

}
