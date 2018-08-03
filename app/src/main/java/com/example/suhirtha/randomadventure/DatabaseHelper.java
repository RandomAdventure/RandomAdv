package com.example.suhirtha.randomadventure;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.suhirtha.randomadventure.models.DatabaseRestaurant;
import com.example.suhirtha.randomadventure.models.RestaurantDao;

/**
 * Created by anitac on 7/25/18.
 */

@Database(entities = {DatabaseRestaurant.class}, version = 4)
public abstract class DatabaseHelper extends RoomDatabase {
    public abstract RestaurantDao restaurantDao();
}
