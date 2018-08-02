package com.example.suhirtha.randomadventure.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by anitac on 8/2/18.
 */

@Entity
public class DatabaseRestaurant {

    @PrimaryKey(autoGenerate = true)
    private  int id;

    @ColumnInfo(name = "yelp_id")
    private  String yelpId;

    @ColumnInfo(name = "restaurant_name")
    protected String restaurantName;

    public DatabaseRestaurant(String yelpId, String restaurantName) {
        this.yelpId = yelpId;
        this.restaurantName = restaurantName;
    }

    public String getYelpId() {
        return yelpId;
    }

    public void setYelpId(String yelpId) {
        this.yelpId = yelpId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
