package com.example.suhirtha.randomadventure;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.suhirtha.randomadventure.models.Restaurant;

import java.util.ArrayList;

public class RestaurantsChosenActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private Context context;
    private YelpClient client;
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdaptor;
    RecyclerView lvitems;
    ArrayList<Restaurant> restaurants;
    RestaurantAdapter restaurantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_chosen);
        context = getApplicationContext();
        db = new DatabaseHelper(context);
        //restaurantAdapter = new RestaurantAdapter(items);
        client = new YelpClient();
        itemsAdaptor = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        lvitems = (RecyclerView) findViewById(R.id.recyclerRestaurant);


        //Restaurant test = new Restaurant("8dUaybEPHsZMgr1iKgqgMQ", "Sotto Mare Oysteria");

        //db.addRestaurantSelected(test);

        // TO SHOW ALL DATABASES
        showRestaurants();
    }

    private void showRestaurants() {
        Cursor res = db.getAllData();
        if(res.getCount() == 0){
            Toast.makeText(this , "Database empty", Toast.LENGTH_LONG).show();
        }
        else{
            StringBuffer stringBuffer = new StringBuffer();
            while (res.moveToNext()){
                stringBuffer.append("Id :"+ res.getString(0) + "\n");
                stringBuffer.append("YelpId :"+ res.getString(1) + "\n");
                stringBuffer.append("RestaurantName :"+ res.getString(2) + "\n\n");

            }
            Log.d("RestaurantChosenAct" , stringBuffer.toString());
        }


    }

}
