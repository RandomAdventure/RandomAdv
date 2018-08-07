package com.example.suhirtha.randomadventure.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.akexorcist.googledirection.model.Direction;
import com.example.suhirtha.randomadventure.Location;
import com.example.suhirtha.randomadventure.LocationListenerRandomAdv;
import com.example.suhirtha.randomadventure.R;
import com.example.suhirtha.randomadventure.fragments.LoadingFragment;
import com.example.suhirtha.randomadventure.fragments.ResultFragment;
import com.example.suhirtha.randomadventure.models.Restaurant;
import com.example.suhirtha.randomadventure.models.ResultActivityModel;
import com.example.suhirtha.randomadventure.viewModels.ResultViewModel;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;


/**
 * Created by togata on 7/16/18.
 */

public class ResultActivity extends AppCompatActivity implements LocationListenerRandomAdv, ResultFragment.ResultActivityListener{

    public ResultViewModel model;
    private Context context;
    public ResultActivity activity;
    public ResultFragment resultFragment;
    private double latitude;
    private double longitude;
    public Restaurant passedRestaurant;
    public Direction directions;
    public String name;
    public float rating;
    public String address;
    public String phoneNumber;
    public LatLng destination;
    public LatLng origin;
    public boolean hasDelivery;
    public boolean takesReservation;
    public FragmentManager fragmentManager;
    public LoadingFragment loadingFragment;
    public FragmentTransaction fragmentTransaction;
    private boolean bundleNull;
    private ResultActivityModel resultActivityModel;
    private String price;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blank_result_activity);
        model= ViewModelProviders.of(this).get(ResultViewModel.class);

        fragmentManager = getSupportFragmentManager();

        if (savedInstanceState == null) {
            fragmentTransaction = fragmentManager.beginTransaction();
            resultFragment = new ResultFragment();
            model.resultFragment = resultFragment;
            loadingFragment = new LoadingFragment();
            fragmentTransaction.add(R.id.rsaFrameLayout, loadingFragment, "loading_fragment");
            fragmentTransaction.hide(resultFragment);
            fragmentTransaction.commit();
            bundleNull = true;
        }
        else{
            loadingFragment = (LoadingFragment) getSupportFragmentManager().findFragmentByTag("loading_fragment");
            resultFragment = (ResultFragment) getSupportFragmentManager().findFragmentByTag("result_fragment");
            bundleNull = false;
        }

        passedRestaurant = (Restaurant) Parcels.unwrap(getIntent().getParcelableExtra("test1"));
        context = this.getApplicationContext();
        activity = this;

        Location currentLocation = new com.example.suhirtha.randomadventure.Location(context, activity, this);
        origin = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        fetchRestaurantInfo();
    }

    public void fetchRestaurantInfo() {
        final Observer<JSONObject> restuarantInfoObserver = new Observer<JSONObject>() {
            @SuppressLint("ResourceType")
            @Override
            public void onChanged(@Nullable JSONObject restuarant) {
                try {
                    name = restuarant.getString("name");
                    rating = (float) restuarant.getDouble("rating");
                    JSONObject location = restuarant.getJSONObject("location");
                    address = location.getString("address1") + ", " + location.getString("city") + ", " + location.getString("state") + " " + location.getString("zip_code");
                    phoneNumber = restuarant.getString("display_phone");

                    JSONObject coordinates = restuarant.getJSONObject("coordinates");
                    latitude = (double) coordinates.get("latitude");
                    longitude = (double) coordinates.get("longitude");
                    destination = new LatLng(latitude, longitude);
                    beginMappingDirections();

                    JSONArray transactions = restuarant.getJSONArray("transactions");
                    for (int i = 0; i < transactions.length(); i++) {
                        if (transactions.get(i).equals("delivery")) {
                            hasDelivery = true;
                        }
                        if (transactions.get(i).equals("restaurant_reservation")) {
                            takesReservation = true;
                        }
                    }

                    price = restuarant.getString("price");
                    resultActivityModel = new ResultActivityModel(origin, destination, model.transportationMode, name, address, rating, hasDelivery, takesReservation, phoneNumber, price);
                    fragmentTransaction = fragmentManager.beginTransaction();
                    if (bundleNull) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("model", resultActivityModel);
                        resultFragment.setArguments(bundle);
                        fragmentTransaction.add(R.id.rsaFrameLayout, resultFragment, "result_fragment");
                    }
                    fragmentTransaction.hide(activity.loadingFragment);
                    fragmentTransaction.show(activity.resultFragment);
                    fragmentTransaction.commit();

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
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CALL_PHONE}, 1);
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
        model.transportationMode = "walking";
        model.resultFragment.resultActivityModel.setTransportationMode("walking");
        final Observer<Direction> walkingDirectionObserver = new Observer<Direction>() {
            @Override
            public void onChanged(@Nullable final Direction direction) {
                activity.directions = direction;
                resultFragment.populateUserInterface(direction);
            }
        };
        model.getWalkingDirections(getResources().getString(R.string.google_maps_api_key), origin, destination).observe(this, walkingDirectionObserver);
    }

    public void drivingDirections(View v){
        model.transportationMode = "driving";
        model.resultFragment.resultActivityModel.setTransportationMode("driving");
        final Observer<Direction> drivingDirectionObserver = new Observer<Direction>() {
            @Override
            public void onChanged(@Nullable final Direction direction) {
                resultFragment.populateUserInterface(direction);
            }
        };
        model.getDrivingDirections(getResources().getString(R.string.google_maps_api_key), origin, destination).observe(this, drivingDirectionObserver);
    }

    @SuppressLint("ResourceType")
    @Override
    public void locationChange(LatLng newLocation){
        origin = newLocation;
        beginMappingDirections();
    }

    @Override
    @SuppressLint("ResourceType")
    public void beginMappingDirections(){
        if (resultFragment.googleMap != null && destination != null){
            if (model.transportationMode.equals("walking")) {
                walkingDirections((View) findViewById(R.layout.activity_result));
            }
            else{
                drivingDirections((View) findViewById(R.layout.activity_result));
            }
        }
    }

}
