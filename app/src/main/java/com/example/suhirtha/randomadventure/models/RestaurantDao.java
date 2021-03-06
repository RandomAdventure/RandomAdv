package com.example.suhirtha.randomadventure.models;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by anitac on 8/2/18.
 */

@Dao
public interface RestaurantDao {
    @Query("SELECT * FROM restaurants")
    LiveData<List<DatabaseRestaurant>> getAllRestaurants();

    @Query("SELECT * FROM restaurants")
    List<DatabaseRestaurant> getAllRestaurantsLive();

    @Insert
    void insert(DatabaseRestaurant restaurants);

    @Insert
    void insertAll(DatabaseRestaurant... restaurants);

    @Delete
    void delete(DatabaseRestaurant restaurants);

    @Query("DELETE FROM restaurants")
    void deleteAll();

    @Query("UPDATE restaurants SET rating =:rating, comment =:comment WHERE id=:id")
    void  updateCritique(int id, Double rating, String comment);

    @Update
    void update(DatabaseRestaurant restaurant);

    @Query("SELECT * FROM restaurants WHERE id LIKE :id ")
    DatabaseRestaurant findRestaurantsById(int id);

    @Query("SELECT * FROM restaurants  WHERE   id = (SELECT MAX(id)  FROM restaurants)")
    DatabaseRestaurant findLastRestaurant();
}
