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
import com.example.suhirtha.randomadventure.models.Restaurant;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by togata on 7/25/18.
 */

public class ResultViewModel extends ViewModel {
    private MutableLiveData<Direction> walkingDirections;
    private MutableLiveData<Direction> drivingDirections;
    private MutableLiveData<JSONObject> restaurant;
    private RestaurantListener restaurantListener;
    private final OkHttpClient client = new OkHttpClient();

    public ResultViewModel(){}

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

    public MutableLiveData<JSONObject> getBusinessInfo(String api_key, String businessId){
        if (restaurant == null) {
            restaurant = new MutableLiveData<JSONObject>();
            Request request = new Request.Builder()
                    .url("https://api.yelp.com/v3/businesses/" + businessId)
                    .addHeader("Authorization", "Bearer " + api_key)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try (ResponseBody responseBody = response.body()) {
                        if (!response.isSuccessful())
                            throw new IOException("Unexpected code " + response);

                        Headers responseHeaders = response.headers();
                        for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                        }
                        String data = responseBody.string();
                        try {
                            JSONObject object = new JSONObject(data);
                            Log.d("Business", object.getString("name"));
                            //restaurantListener.restaurantInfo(object);
                            restaurant.postValue(object);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        return restaurant;
    }

}
