package com.example.suhirtha.randomadventure;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

/**
 * Created by togata on 7/26/18.
 */

public interface LocationListenerRandomAdv {
    public void locationChange(LatLng newLocation);
}