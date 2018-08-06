package com.example.suhirtha.randomadventure.viewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.suhirtha.randomadventure.models.DatabaseRestaurant;

import java.util.List;

/**
 * Created by anitac on 8/3/18.
 */

public class RestaurantsChosenViewModel extends ViewModel {

    private MutableLiveData<List<DatabaseRestaurant>> restaurants;

    public LiveData<List<DatabaseRestaurant>> getUsers() {
        if (restaurants == null) {
            restaurants = new MutableLiveData<List<DatabaseRestaurant>>();
            loadRestaurants();
        }
        return restaurants;
    }

    private void loadRestaurants() {
        // Do an asynchronous operation to fetch users.
    }
}
