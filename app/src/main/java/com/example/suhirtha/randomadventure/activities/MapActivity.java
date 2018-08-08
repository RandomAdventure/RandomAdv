package com.example.suhirtha.randomadventure.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity; 
import android.os.Bundle;
import android.view.animation.BounceInterpolator;

import com.example.suhirtha.randomadventure.R;
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
import android.os.Handler;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private MapView mMap;
    private ArrayList<LatLng> previousLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        //TODO -- grab latitude and longitude from database
        previousLocations = new ArrayList<LatLng>();
        previousLocations.add(new LatLng(29.979492, 31.134132));
        previousLocations.add(new LatLng(42.359972, -71.092036));
        previousLocations.add(new LatLng(40.824751, -73.336089));
        previousLocations.add(new LatLng(48.860823, 2.337547));
        mMap = (MapView) findViewById(R.id.maMap);
        mMap.onCreate(savedInstanceState);
        mMap.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        //TODO -- make i ok for it to only have one object in array and not throw error
        double lowestLat;
        double highestLat;
        double lowestLong = 0;
        double highestLong = 0;
        if (previousLocations.get(0).latitude<previousLocations.get(1).latitude){
            lowestLat = previousLocations.get(0).latitude;
            highestLat = previousLocations.get(1).latitude;
        }
        else{
            lowestLat = previousLocations.get(1).latitude;
            highestLat = previousLocations.get(0).latitude;
        }
        if (previousLocations.get(0).longitude<previousLocations.get(1).longitude){
            lowestLong = previousLocations.get(0).longitude;
            highestLong = previousLocations.get(1).longitude;
        }
        else{
            lowestLong = previousLocations.get(1).longitude;
            highestLong = previousLocations.get(0).longitude;
        }
        for (int i = 0; i < previousLocations.size(); i ++){
            Marker marker = googleMap.addMarker(new MarkerOptions()
                    .position(previousLocations.get(i))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
            //.title(title)
            //.snippet(snippet)
            //.icon(defaultMarker));
            dropPinEffect(marker);
            if (previousLocations.get(i).latitude<lowestLat){
                lowestLat = previousLocations.get(i).latitude;
            }
            if (previousLocations.get(i).latitude>highestLat){
                highestLat = previousLocations.get(i).latitude;
            }
            if (previousLocations.get(i).longitude<lowestLong){
                lowestLong = previousLocations.get(i).longitude;
            }
            if (previousLocations.get(i).longitude>highestLong){
                highestLong = previousLocations.get(i).longitude;
            }

        }

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(new LatLng(lowestLat, lowestLong));
        builder.include(new LatLng(highestLat, highestLong));
        LatLngBounds bounds = builder.build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 60);
        googleMap.animateCamera(cameraUpdate);

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

    private void dropPinEffect(final Marker marker) {
        // Handler allows us to repeat a code block after a specified delay
        final android.os.Handler handler = new android.os.Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 1500;

        // Use the bounce interpolator
        final android.view.animation.Interpolator interpolator =
                new BounceInterpolator();

        // Animate marker with a bounce updating its position every 15ms
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                // Calculate t for bounce based on elapsed time
                float t = Math.max(
                        1 - interpolator.getInterpolation((float) elapsed
                                / duration), 0);
                // Set the anchor
                marker.setAnchor(0.5f, 1.0f + 14 * t);

                if (t > 0.0) {
                    // Post this event again 15ms from now.
                    handler.postDelayed(this, 15);
                } else { // done elapsing, show window
                    marker.showInfoWindow();
                }
            }
        });
    }
}
