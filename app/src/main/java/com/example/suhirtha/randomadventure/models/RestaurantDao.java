package com.example.suhirtha.randomadventure.models;

import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by anitac on 8/2/18.
 */

public interface RestaurantDao {
    @Query("SELECT * FROM restautant")
    List<DatabaseRestaurant> getAllRestaurants();

    @Insert
    void insertAll(DatabaseRestaurant... restaurants);
}
