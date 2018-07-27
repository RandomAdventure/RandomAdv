package com.example.suhirtha.randomadventure.models;

import android.app.Activity;
import android.content.Context;

import com.example.suhirtha.randomadventure.Location;
import com.example.suhirtha.randomadventure.LocationListenerRandomAdv;
import com.google.android.gms.maps.model.LatLng;

public class UserRequest {

    private double userLatitude;
    private double userLongitude;
    private int radius; //in miles
    private String term;
    private float minRating = 0; //cannot be implemented here
    private int maxPrice = 0;
    private String[] attributes;
    private String priceString;
    Location userLocation;

    private boolean attributesAdded;
    private boolean termAdded;
    private boolean maxPriceSet;

    private String completeURL;

//--------------------------------------------------------------------------------------------------

    public static final String BASE_URL = "https://api.yelp.com/v3/businesses/search";

//--------------------------------------------------------------------------------------------------
    public UserRequest(Context context, Activity currentActivity) {

        userLocation = new Location(context, currentActivity, new LocationListenerRandomAdv() {
            @Override
            public void locationChange(LatLng newLocation) {
                return;
            }
        });
        userLatitude = userLocation.getLatitude();
        userLongitude = userLocation.getLongitude();
        completeURL = BASE_URL; //bc strings are immutable!! the only thing I learned in school

    }
//--------------------------------------------------------------------------------------------------
    /* For future reference: If the setters return the actual instance of the UserRequest class, then
     * creating a customized UserRequest will be a lot easier:
     * UserRequest request = new UserRequest().setRadius(*).setCuisine(*).setMinRating(*)... etc.
     * Conventional syntax of the 'builder pattern'
     */

    public UserRequest setRadius(int radius) {
        this.radius = radius;
        return this;
    }

    public UserRequest setCuisine(String cuisine) {
        this.term = cuisine;
        termAdded = true;
        return this;
    }

    public UserRequest setMinRating(float minRating) {
        this.minRating = minRating;
        return this;
    }

    public UserRequest setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
        maxPriceSet = true;
        return this;
    }

    public UserRequest setAttributes(String[] attributes) {
        this.attributes = attributes;
        attributesAdded = true;
        return this;
    }

//--------------------------------------------------------------------------------------------------
    public UserRequest buildURL() {
        //TODO - confirm: order of params does not matter?
        switch (maxPrice) {
            case 0: priceString = "1, 2, 3, 4";
            case 1: priceString = "1";
            case 2: priceString = "1, 2";
            case 3: priceString = "1, 2, 3";
            case 4: priceString = "1, 2, 3, 4";
        }

        //Add user location
        completeURL += "?latitude=" + this.userLatitude + "&longitude=" + this.userLongitude;

        //Add price parameter
        if (maxPriceSet) {
            completeURL += "&price=" + this.priceString;
        }

        if (termAdded) {
            //Add term(s)
            completeURL += "&term=" + this.term;
        }

        //Add attributes one by one
        if (attributesAdded && attributes.length != 0) {
            completeURL += "&attributes=";
            completeURL += attributes[0];
        }
        if (attributesAdded && attributes.length > 1) {
            for (int i = 1; i < attributes.length; i++) {
                completeURL += "," + attributes[i]; //TODO - avoid concat in loops: use StringBuilder
            }

        }

        return this;
    }
//--------------------------------------------------------------------------------------------------
    public String getCompleteURL() {
        return this.completeURL;
    }
}
