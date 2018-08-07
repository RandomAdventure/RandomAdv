package com.example.suhirtha.randomadventure.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by anitac on 8/2/18.
 */

@Entity (tableName = "restaurant")
public class DatabaseRestaurant {

    @PrimaryKey(autoGenerate = true)
    private  int id;

    @ColumnInfo(name = "yelp_id")
    private  String yelpId;

    @ColumnInfo(name = "restaurant_name")
    protected String restaurantName;

//    @ColumnInfo(name = "rating")
//    private  String ratings;
//
//    @ColumnInfo(name = "comment")
//    private String comment;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
