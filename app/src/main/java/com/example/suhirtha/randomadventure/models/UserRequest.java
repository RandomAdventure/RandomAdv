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
    private String attribute;
    private String term;
    private float minRating = 0; //cannot be implemented here
    private int maxPrice = 0;
    private String terms;
    private String priceString;
    Location userLocation;

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
        // radius may be null
        this.radius = radius;
        return this;
    }

    public UserRequest setAttribute(String attribute) {
        this.term = attribute;
        return this;
    }

    public UserRequest setMinRating(float minRating) {
        this.minRating = minRating;
        return this;
    }

    //TODO - what is the value of maxPrice if user leaves the editText empty? 0?
    public UserRequest setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
        maxPriceSet = true;
        return this;
    }

    public UserRequest setTerms(String terms) {
        //attributes[] may be null
        this.terms = terms;
        return this;
    }

//--------------------------------------------------------------------------------------------------
    public UserRequest buildURL() {

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

        if (attribute != null) {
            //Add term(s)
            completeURL += "&term=" + this.attribute;
        }

        /**
        //Add terms one by one
        if (terms != null && terms.size() != 0) {
            completeURL += "&term=";
            completeURL += terms.get(0);
        }
        if (terms != null && terms.size() > 1) {
            for (int i = 1; i < terms.size(); i++) {
                completeURL += "," + terms.get(0); //TODO - avoid concat in loops: use StringBuilder
            }

        }
         **/

        if (terms != null && terms.length() > 0) {
            completeURL = completeURL + "&term=" + terms;
        }

        return this;
    }
//--------------------------------------------------------------------------------------------------
    public String getCompleteURL() {
        return this.completeURL;
    }
}
