package com.example.suhirtha.randomadventure;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.suhirtha.randomadventure.models.Restaurant;

/**
 * Created by anitac on 7/25/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sInstance;

    // Database Info
    private static final String DATABASE_NAME = "restaurantDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_RESTAURANTS = "restaurants";

    // Restaurant table  Columns
    private static final String KEY_RESTAURANT_ID = "id";
    private static final String KEY_RESTAURANT_YELP_ID = "yelpId";
    private static final String KEY_RESTAURANT_NAME = "restaurantName";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_RESTAURANT_TABLE = "CREATE TABLE " + TABLE_RESTAURANTS +
                "(" +
                KEY_RESTAURANT_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_RESTAURANT_YELP_ID + " TEXT," +
                KEY_RESTAURANT_NAME + " TEXT" +
                ")";
        sqLiteDatabase.execSQL(CREATE_RESTAURANT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTAURANTS);
        onCreate(sqLiteDatabase);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    public void addRestaurantSelected(Restaurant restaurant) {
        // Create and/or open the database for writing
        SQLiteDatabase db = getWritableDatabase();

        // It's a good idea to wrap our insert in a transaction. This helps with performance and ensures
        // consistency of the database.
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_RESTAURANT_NAME, restaurant.getId());
            values.put(KEY_RESTAURANT_YELP_ID, restaurant.getName());

            // Notice how we haven't specified the primary key. SQLite auto increments the primary key column.
            db.insertOrThrow(TABLE_RESTAURANTS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("DatabaseHelper", "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

}
