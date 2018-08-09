package com.example.suhirtha.randomadventure.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by anitac on 8/2/18.
 */

@Entity (tableName = "restaurants")
public class DatabaseRestaurant {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private  int id;

    @ColumnInfo(name = "yelp_id")
    private  String yelpId;

    @ColumnInfo(name = "restaurant_name")
    private String restaurantName;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "rating")
    private  Double rating;

    @ColumnInfo(name = "comment")
    private String comment;

    @ColumnInfo(name = "latitude")
    private double latitude;

    @ColumnInfo(name = "longitude")
    private  double longitude;


    public DatabaseRestaurant(String yelpId, String restaurantName, String address, Double rating, String comment, double latitude, double longitude) {
        this.yelpId = yelpId;
        this.restaurantName = restaurantName;
        this.address = address;
        this.rating = rating;
        this.comment = comment;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
