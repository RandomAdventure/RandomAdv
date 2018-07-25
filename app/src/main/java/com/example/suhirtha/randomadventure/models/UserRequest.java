package com.example.suhirtha.randomadventure.models;

import android.app.Activity;
import android.content.Context;

import com.example.suhirtha.randomadventure.Location;

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

    private String completeURL;

    public static final String BASE_URL = "https://api.yelp.com/v3/businesses/search";


    public UserRequest(Context context, Activity currentActivity) {

        userLocation = new Location(context, currentActivity);
        userLatitude = userLocation.getLatitude();
        userLongitude = userLocation.getLongitude();
        completeURL = BASE_URL; //bc strings are immutable!! the only thing I learned in school

    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setCuisine(String cuisine) {
        this.term = cuisine;
        termAdded = true;
    }

    public void setMinRating(float minRating) {
        this.minRating = minRating;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setAttributes(String[] attributes) {
        this.attributes = attributes;
        attributesAdded = true;
    }


    public void buildURL() {
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
        if (maxPrice != 0) {
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
    }

    public String getCompleteURL() {
        return this.completeURL;
    }
}
