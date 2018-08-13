package com.example.suhirtha.randomadventure.activities;

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
import android.widget.Toast;

import com.example.suhirtha.randomadventure.R;
import com.example.suhirtha.randomadventure.adapters.RecyclerViewAdapter;
import com.example.suhirtha.randomadventure.fragments.SelectionFragment;
import com.example.suhirtha.randomadventure.models.DataModel;
import com.example.suhirtha.randomadventure.models.Restaurant;
import com.example.suhirtha.randomadventure.models.UserRequest;
import com.example.suhirtha.randomadventure.viewModels.SelectionViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;


public class SelectionActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemListener, SelectionFragment.SelectionListener {

    private static ArrayList<Restaurant> fiveRest;
    public String[] suggestions;
    //--------------------------------------------------------------------------------------------------
    final Fragment selection = new SelectionFragment();
    private FragmentTransaction fragmentTransaction1;
    private FragmentManager fragmentManager;
//--------------------------------------------------------------------------------------------------

    //--------------------------------------------------------------------------------------------------
    Restaurant test1 = new Restaurant("8dUaybEPHsZMgr1iKgqgMQ", "Sotto Mare Oysteria");
    //--------------------------------------------------------------------------------------------------
    UserRequest request;

    SelectionViewModel viewModel;
//--------------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);

        fiveRest = new ArrayList<>(); //initialize the holder arrayList

        viewModel = ViewModelProviders.of(this).get(SelectionViewModel.class);

        fragmentManager = getSupportFragmentManager();

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

        if (restaurantList.length() < 5) {
            Log.d("Not enough restaurants", "Found fewer than 5 restaurants.");
            Toast.makeText(this, "We could not find enough restaurants that matched your specifications, please try again!",
                     Toast.LENGTH_LONG).show();
            Intent myIntent = getIntent();
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            this.startActivity(myIntent);
            overridePendingTransition(0,0);
            return;
        }


        ArrayList<Integer> randomNumbers = new ArrayList<>();
        int randomNum;


        //creates list of random numbers that correspond to index of restaurant to choose from JSON results
        while(randomNumbers.size() < 10) {
            randomNum = (int) (Math.random() * restaurantList.length());
            if (!randomNumbers.contains(randomNum)) { //ensures that the same restaurant isn't chosen
                randomNumbers.add(randomNum);
                Log.d("RandomNumberChosen", randomNum + "");

            }
        }

        int randomNumIndex = 0;

        while (fiveRest.size() < 5) {
            Restaurant restaurant;
            try {

                if (randomNumIndex >= 10) {
                    Log.d("hello", "suhi");
                    Toast.makeText(this, "Looks like your query returned food trucks! " +
                            "Please try again.", Toast.LENGTH_LONG).show();
                    Intent myIntent = getIntent();
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    this.startActivity(myIntent);
                    overridePendingTransition(0,0);
                    return;

                }
                int index = randomNumbers.get(randomNumIndex);
                Log.d("Index of random numbers", index + "");
                randomNumIndex++;
                JSONObject chosenRest = restaurantList.getJSONObject(index);
                boolean isFoodTruck = false;

                //check if chosen restaurant is a food truck
                JSONArray categories = chosenRest.getJSONArray("categories");
                for (int i = 0; i < categories.length(); i++) {
                    if (categories.getJSONObject(i).getString("alias").equals("foodtrucks")) {
                        isFoodTruck = true;
                        Log.d("Found a food truck", chosenRest.getString("name"));
                        break;
                    }
                }

                if (isFoodTruck) {
                    continue;
                } else {
                    restaurant = new Restaurant (chosenRest.getString("id"), chosenRest.getString("name"));
                    fiveRest.add(restaurant);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        for (int o = 0; o < 5; o++) {
            Log.d("Pass to Anna", fiveRest.get(o).getName());
        }

        //passes intent with first five restaurants to Anna
        Intent anna = new Intent(SelectionActivity.this, RandomizeActivity.class);
        anna.putExtra("restaurants", Parcels.wrap(fiveRest));
        startActivity(anna);


    }

//--------------------------------------------------------------------------------------------------

    @Override
    public void onItemClick(DataModel item) {
        Toast.makeText(getApplicationContext(), item.title + " is clicked", Toast.LENGTH_SHORT).show();
    }

//--------------------------------------------------------------------------------------------------

    @Override
    public void makeRequest(final UserRequest request) {
        try {

            this.request = request;
            final Observer<JSONArray> restaurantObserver = new Observer<JSONArray>() {
                @Override
                public void onChanged(@Nullable JSONArray restaurants) {
                    resultsReturned(viewModel.getRestaurants(request).getValue());
                }
            };
            viewModel.getRestaurants(request).observe(SelectionActivity.this, restaurantObserver);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//--------------------------------------------------------------------------------------------------





//--------------------------------------------------------------------------------------------------


}