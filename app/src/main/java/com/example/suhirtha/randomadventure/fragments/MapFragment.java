package com.example.suhirtha.randomadventure.fragments;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.suhirtha.randomadventure.AppDatabase;
import com.example.suhirtha.randomadventure.R;
import com.example.suhirtha.randomadventure.models.DatabaseRestaurant;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by togata on 8/9/18.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private MapView mMap;
    private GoogleMap googleMap;
    private ArrayList<LatLng> previousLocations;
    private List<DatabaseRestaurant> databaseRestaurants;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstances) {

        v = inflater.inflate(R.layout.activity_map, container, false);
        previousLocations = new ArrayList<LatLng>();
        AppDatabase db = Room.databaseBuilder(getContext(), AppDatabase.class, "saved_restaurants")
                .allowMainThreadQueries()
                .build();
        databaseRestaurants = db.restaurantDao().getAllRestaurantsLive();
        int length = databaseRestaurants.size();
        for (int i = 0; i < length; i++){
            DatabaseRestaurant tempRestaurant = databaseRestaurants.get(i);
            LatLng temp = new LatLng(tempRestaurant.getLatitude(), tempRestaurant.getLongitude());
            previousLocations.add(temp);
        }
        mMap = (MapView) v.findViewById(R.id.maMap);
        mMap.onCreate(savedInstances);
        mMap.getMapAsync(this);
        return v;
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                return false;
            }
        });
        double lowestLat = 0;
        double highestLat = 0;
        double lowestLong = 0;
        double highestLong = 0;
        if (previousLocations.size()>=2) {
            if (previousLocations.get(0).latitude < previousLocations.get(1).latitude) {
                lowestLat = previousLocations.get(0).latitude;
                highestLat = previousLocations.get(1).latitude;
            } else {
                lowestLat = previousLocations.get(1).latitude;
                highestLat = previousLocations.get(0).latitude;
            }
            if (previousLocations.get(0).longitude < previousLocations.get(1).longitude) {
                lowestLong = previousLocations.get(0).longitude;
                highestLong = previousLocations.get(1).longitude;
            } else {
                lowestLong = previousLocations.get(1).longitude;
                highestLong = previousLocations.get(0).longitude;
            }
            for (int i = 0; i < previousLocations.size(); i++) {
                Marker marker = googleMap.addMarker(new MarkerOptions()
                        .position(previousLocations.get(i))
                        .title(databaseRestaurants.get(i).getRestaurantName())
                        .snippet(databaseRestaurants.get(i).getComment())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                marker.hideInfoWindow();
                dropPinEffect(marker);
                if (previousLocations.get(i).latitude < lowestLat) {
                    lowestLat = previousLocations.get(i).latitude;
                }
                if (previousLocations.get(i).latitude > highestLat) {
                    highestLat = previousLocations.get(i).latitude;
                }
                if (previousLocations.get(i).longitude < lowestLong) {
                    lowestLong = previousLocations.get(i).longitude;
                }
                if (previousLocations.get(i).longitude > highestLong) {
                    highestLong = previousLocations.get(i).longitude;
                }

            }
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(new LatLng(lowestLat, lowestLong));
            builder.include(new LatLng(highestLat, highestLong));
            LatLngBounds bounds = builder.build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 80);
            googleMap.animateCamera(cameraUpdate);
        }

        else{
            for (int i = 0; i < previousLocations.size(); i++) {
                Marker marker = googleMap.addMarker(new MarkerOptions()
                        .position(previousLocations.get(i))
                        .title(databaseRestaurants.get(i).getRestaurantName())
                        .snippet(databaseRestaurants.get(i).getComment())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
                marker.hideInfoWindow();
                dropPinEffect(marker);
                if (previousLocations.get(i).latitude < lowestLat) {
                    lowestLat = previousLocations.get(i).latitude;
                }
                if (previousLocations.get(i).latitude > highestLat) {
                    highestLat = previousLocations.get(i).latitude;
                }
                if (previousLocations.get(i).longitude < lowestLong) {
                    lowestLong = previousLocations.get(i).longitude;
                }
                if (previousLocations.get(i).longitude > highestLong) {
                    highestLong = previousLocations.get(i).longitude;
                }

            }
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(new LatLng(lowestLat, lowestLong));
            builder.include(new LatLng(highestLat, highestLong));
            LatLngBounds bounds = builder.build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 80);
            googleMap.animateCamera(cameraUpdate);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
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

    private void dropPinEffect(final Marker marker) {
        final android.os.Handler handler = new android.os.Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 1500;

        final android.view.animation.Interpolator interpolator =
                new BounceInterpolator();

        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = Math.max(
                        1 - interpolator.getInterpolation((float) elapsed
                                / duration), 0);
                marker.setAnchor(0.5f, 1.0f + 14 * t);

                if (t > 0.0) {
                    handler.postDelayed(this, 15);
                } else {
                    //marker.showInfoWindow();
                }
            }
        });
    }
}
