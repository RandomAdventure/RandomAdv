package com.example.suhirtha.randomadventure;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import com.example.suhirtha.randomadventure.models.Restaurant;
import com.example.suhirtha.randomadventure.models.UserRequest;

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
    private EditText mTestRadius;
    private EditText mTestPrice;

//--------------------------------------------------------------------------------------------------
    Restaurant test1 = new Restaurant("8dUaybEPHsZMgr1iKgqgMQ", "Sotto Mare Oysteria");
//--------------------------------------------------------------------------------------------------

    UserRequest request;

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
        mTestRadius = findViewById(R.id.etTestRadius);
        mTestPrice = findViewById(R.id.etTestPrice);
        firstFive = new ArrayList<>(); //initialize the holder arrayList

        final SelectionViewModel viewModel = ViewModelProviders.of(this).get(SelectionViewModel.class);

        //------------------------------------------------------------------------------------------

        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    createRequest();
                    //passRestaurants();
                    final Observer<JSONArray> restaurantObserver = new Observer<JSONArray>() {
                        @Override
                        public void onChanged(@Nullable JSONArray restaurants) {
                            Log.d("hello", "Restaurants received");
                            Log.d("", "");
                            resultsReturned(viewModel.getRestaurants(request).getValue());
                        }
                    };
                    viewModel.getRestaurants(request).observe(SelectionActivity.this, restaurantObserver);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //------------------------------------------------------------------------------------------

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

//--------------------------------------------------------------------------------------------------
    /**
     * currently: takes the first five restaurants in restaurantList, populates 'firstFive',
     * and passes them to Anna
     * @param restaurantList - the full list of relevant restaurants returned by YelpClient
     * @throws Exception - related to faulty API calls
     */
    public void resultsReturned(JSONArray restaurantList) {
        Log.d("Print received", "Number of restaurants received: " + restaurantList.length() + "---" + restaurantList.toString());
        //generate random integer array from 1-20 (20 because Yelp returns 20 restaurants by default per API call)
        ArrayList<Integer> randomNumbers = new ArrayList<>();
        int randomNum;
        while(randomNumbers.size() < 5) {
            randomNum = (int) (Math.random() * 20);
            if (!randomNumbers.contains(randomNum)) { //ensures that the same restaurant isn't chosen
                randomNumbers.add(randomNum);
            }
        }
        Log.d("Random ints arrayList", randomNumbers.toString());
        for (int i = 0; i < 5; i++) {
            Restaurant restaurant = null;
            try {
                restaurant = new Restaurant
                        (restaurantList.getJSONObject(randomNumbers.get(i)).getString("id"),
                         restaurantList.getJSONObject(randomNumbers.get(i)).getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            firstFive.add(restaurant);
            Log.d("To send to Anna", firstFive.toString());
        }

        //passes intent with first five restaurants to Anna
        Intent anna = new Intent(SelectionActivity.this, RandomizeActivity.class);
        anna.putExtra("restaurants", Parcels.wrap(firstFive));
        startActivity(anna);

    }

//--------------------------------------------------------------------------------------------------
    public void createRequest() {

        request = new UserRequest(this, this)
                    .setRadius(Integer.parseInt(mTestRadius.getText().toString()))
                    .setMaxPrice(Integer.parseInt(mTestPrice.getText().toString())) //TODO - max price and radius may not have been provided
                    .buildURL();
    }
//--------------------------------------------------------------------------------------------------
}

