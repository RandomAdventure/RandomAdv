package com.example.suhirtha.randomadventure;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.suhirtha.randomadventure.models.DatabaseRestaurant;
import com.example.suhirtha.randomadventure.models.RestaurantDao;

/**
 * Created by anitac on 7/25/18.
 */

@Database(entities = {DatabaseRestaurant.class}, version = 8)
public abstract class AppDatabase extends RoomDatabase {
    public abstract RestaurantDao restaurantDao();
    private static AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "saved_restaurants")
                            .fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }


}
