package com.example.suhirtha.randomadventure.models;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by anitac on 8/2/18.
 */

@Dao
public interface RestaurantDao {
    @Query("SELECT * FROM restaurant")
    LiveData<List<DatabaseRestaurant>> getAllRestaurants();

    @Insert
    void insert(DatabaseRestaurant restaurant);

    @Insert
    void insertAll(DatabaseRestaurant... restaurants);

    @Delete
    void delete(DatabaseRestaurant restaurants);

    @Query("DELETE FROM restaurant")
    void deleteAll();

}
