package com.example.suhirtha.randomadventure;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.Log;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Info;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by togata on 7/25/18.
 */

public class ResultViewModel extends ViewModel {
    private MutableLiveData<Direction> walkingDirections;
    private MutableLiveData<Direction> drivingDirections;

    public MutableLiveData<Direction> getWalkingDirections(String api_key, LatLng origin, LatLng destination){
        if (walkingDirections==null){
            walkingDirections = new MutableLiveData<Direction>();
            GoogleDirection.withServerKey(api_key)
                    .from(origin)
                    .to(destination)
                    .transportMode(TransportMode.WALKING)
                    .execute(new DirectionCallback() {
                        @Override
                        public void onDirectionSuccess(Direction direction, String rawBody) {
                            walkingDirections.setValue(direction);
                        }

                        @Override
                        public void onDirectionFailure(Throwable t) {
                            Log.e("getDirections", t.toString());
                        }
                    });
        }
        return walkingDirections;
    }

    public MutableLiveData<Direction> getDrivingDirections(String api_key, LatLng origin, LatLng destination){
        if (drivingDirections==null){
            drivingDirections = new MutableLiveData<Direction>();
            GoogleDirection.withServerKey(api_key)
                    .from(origin)
                    .to(destination)
                    .transportMode(TransportMode.DRIVING)
                    .execute(new DirectionCallback() {
                        @Override
                        public void onDirectionSuccess(Direction direction, String rawBody) {
                            drivingDirections.setValue(direction);
                        }

                        @Override
                        public void onDirectionFailure(Throwable t) {
                            Log.e("getDirections", t.toString());
                        }
                    });
        }
        return drivingDirections;
    }

}
