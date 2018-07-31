package com.example.suhirtha.randomadventure;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Info;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;


import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by togata on 7/27/18.
 */

public class ResultFragment extends Fragment implements OnMapReadyCallback{

    View view;
    private Context context;
    public Activity activity;
    private ResultFragment fragment;
    public ResultViewModel model;
    private TextView mName;
    private RatingBar mRating;
    private TextView mAddress;
    private TextView mPhoneNumber;
    private CheckBox mDelivery;
    private CheckBox mReservation;
    private MapView mMap;
    public GoogleMap googleMap;
    private TextView mDistance;
    private TextView mDuration;
    private ImageButton mWalking;
    private ImageButton mDriving;
    private ResultActivityListener activityListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstances){
        view = inflater.inflate(R.layout.activity_result, container, false);
        setRetainInstance(true);

        mName = (TextView) view.findViewById(R.id.rsaName);
        mAddress = (TextView) view.findViewById(R.id.rsaAddress);
        mRating = (RatingBar) view.findViewById(R.id.rsaRating);
        mPhoneNumber = (TextView) view.findViewById(R.id.rsaPhoneNumber);
        mReservation = (CheckBox) view.findViewById(R.id.rsaReservation);
        mReservation.setClickable(false);
        mDelivery = (CheckBox) view.findViewById(R.id.rsaDelivery);
        mDelivery.setClickable(false);
        mMap = (MapView) view.findViewById(R.id.rsaMap);
        mMap.onCreate(savedInstances);
        mMap.getMapAsync(this);
        mDistance = (TextView) view.findViewById(R.id.rsaDistance);
        mDuration = (TextView) view.findViewById(R.id.rsaDuration);
        mWalking = (ImageButton) view.findViewById(R.id.rsaWalking);
        mDriving = (ImageButton) view.findViewById(R.id.rsaDriving);

        context = view.getContext();
        activity = getActivity();
        activityListener = (ResultActivityListener) activity;
        fragment = this;
        model = ((ResultActivity) getActivity()).model;

        return view;
    }

    public void updateRestaurantInfo() {
        mName.setText(activityListener.getName());
        mRating.setRating(activityListener.getRating());
        mAddress.setText(activityListener.getAddress());
        mPhoneNumber.setText(activityListener.getPhoneNumber());
        if (activityListener.getDeliverySetting() == true) {
            mDelivery.setChecked(true);
        }
        if (activityListener.getReservationSetting()== true) {
            mReservation.setChecked(true);
        }
    }

    public void updateRestaurantDirections(){
        activity.runOnUiThread(new Runnable(){
            public void run(){
                if (activityListener.getTransportationMode().equals("walking")){
                    mWalking.setBackgroundColor(getResources().getColor(R.color.clearPurple));
                    mDriving.setBackgroundColor(getResources().getColor(R.color.clear));
                }
                else{
                    mWalking.setBackgroundColor(getResources().getColor(R.color.clear));
                    mDriving.setBackgroundColor(getResources().getColor(R.color.clearPurple));
                }
                googleMap.clear();
                Route route = activityListener.getDirectionObject().getRouteList().get(0);
                Leg leg = route.getLegList().get(0);
                ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();
                PolylineOptions polylineOptions = DirectionConverter.createPolyline(context, directionPositionList, 5, getResources().getColor(R.color.darkPurple));
                googleMap.addPolyline(polylineOptions);
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(activityListener.getOrigin()));
                googleMap.addMarker(new MarkerOptions().position(activityListener.getOrigin()));
                googleMap.addMarker(new MarkerOptions().position(activityListener.getDestination()));
                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(activityListener.getOrigin());
                builder.include(activityListener.getDestination());
                LatLngBounds bounds = builder.build();
                int padding = 60;
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 900, 750, padding);
                googleMap.animateCamera(cameraUpdate);

                Info distanceInfo = leg.getDistance();
                Info durationInfo = leg.getDuration();
                mDistance.setText(distanceInfo.getText());
                mDuration.setText(durationInfo.getText());
                activityListener.showResultFragment();

            }
        });
    }

    public void populateUserInterface(){
        updateRestaurantInfo();
        updateRestaurantDirections();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        activityListener.beginMappingDirections();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMap.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMap.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMap.onStop();
    }
    @Override
    public void onPause() {
        mMap.onPause();
        super.onPause();
    }
    @Override
    public void onDestroy() {
        mMap.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMap.onLowMemory();
    }

    public interface ResultActivityListener {
        public void showResultFragment();
        public String getName();
        public String getAddress();
        public boolean getDeliverySetting();
        public boolean getReservationSetting();
        public float getRating();
        public String getPhoneNumber();
        public String getTransportationMode();
        public Direction getDirectionObject();
        public LatLng getOrigin();
        public LatLng getDestination();
        public void beginMappingDirections();
    }

}
