package com.example.suhirtha.randomadventure;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.suhirtha.randomadventure.models.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import java.util.ArrayList;


public class SelectionActivity extends AppCompatActivity {

    private static ArrayList<Restaurant> firstFive;
//--------------------------------------------------------------------------------------------------
    final FragmentManager fragmentManager = getSupportFragmentManager();
    final Fragment accordionList = new AccordionFragment();
    private FragmentTransaction fragmentTransaction1;
//--------------------------------------------------------------------------------------------------
    private Button mSearch;
    private Button mDone;
    private SeekBar mRadius;
    private TextView mRadiusDisplay;
    private Spinner mCuisine;
    private EditText mTestLocation;
//--------------------------------------------------------------------------------------------------
    Restaurant test1 = new Restaurant("8dUaybEPHsZMgr1iKgqgMQ", "Sotto Mare Oysteria");
//--------------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        //fragment code
        fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction1.replace(R.id.saPlaceholderFragment, accordionList).commit();

        //initialize fields
        mSearch = findViewById(R.id.btnSearch);
        mDone = findViewById(R.id.btnDone);
        mTestLocation = findViewById(R.id.etTestLocation);
        firstFive = new ArrayList<>(); //initialize the holder arrayList

        //onClickListener for 'Search' button - leads to Anna's randomizer activity
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    passRestaurants();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //onClickListener for 'Done' button - leads to Tatum's result activity
        //TODO - remove eventually: kept for Tatum's testing
        mDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tatum = new Intent(SelectionActivity.this, ResultActivity.class);
                tatum.putExtra("test1", Parcels.wrap(test1));
                startActivity(tatum);
            }
        });

    }

    /**
     * currently: takes the first five restaurants in restaurantList, populates 'firstFive',
     * and passes them to Anna
     * TODO - randomly choose five restaurants, then populate an arrayList, which is then passed to Anna
     * @param restaurantList - the full list of relevant restaurants returned by YelpClient
     * @throws Exception - related to faulty API calls
     */
    public void resultsReturned(JSONArray restaurantList) {
        //currently: picks first five restaurants of returned list
        for (int i = 0; i < 5; i++) {
            Restaurant restaurant = null;
            try {
                restaurant = new Restaurant
                        (restaurantList.getJSONObject(i).getString("id"),
                         restaurantList.getJSONObject(i).getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            firstFive.add(restaurant);
        }

        //passes intent with first five restaurants to Anna
        Intent anna = new Intent(SelectionActivity.this, RandomizeActivity.class);
        anna.putExtra("restaurants", Parcels.wrap(firstFive));
        startActivity(anna);

    }

    /**
     * currently: incorrectly named :( - calls YelpClient class by passing in user parameters
     * TODO - will look a lot more complicated, must account for ALL parameters
     */
    public void passRestaurants() {
        //creates a new YelpClient instance - TODO: static?
        YelpClient client = new YelpClient();
        try {
            //requests with only location, for now
            client.getBusinesses(mTestLocation.getText().toString(), this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

