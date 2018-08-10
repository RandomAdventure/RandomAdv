package com.example.suhirtha.randomadventure.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.suhirtha.randomadventure.AppDatabase;
import com.example.suhirtha.randomadventure.R;
import com.example.suhirtha.randomadventure.adapters.RestaurantAdapter;
import com.example.suhirtha.randomadventure.models.DatabaseRestaurant;
import com.example.suhirtha.randomadventure.viewModels.RestaurantsChosenViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by togata on 8/9/18.
 */

public class RestaurantListFragment extends Fragment implements View.OnLongClickListener  {

    private View v;
    private List<DatabaseRestaurant> restaurants;
    RecyclerView recyclerView;
    RestaurantAdapter adapter;
    int REQUEST_CODE_SELECTION = 150;
    RestaurantsChosenViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstances) {

        v = inflater.inflate(R.layout.activity_restaurants_chosen, container, false);

        final Toolbar toolbar = (Toolbar) v.findViewById(R.id.rcaToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false); //removes title

        restaurants = new ArrayList<DatabaseRestaurant>();
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerRestaurant);
        adapter = new RestaurantAdapter(getActivity(), restaurants, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        viewModel = ViewModelProviders.of(this).get(RestaurantsChosenViewModel.class);

        viewModel.getmRestaurants().observe(getActivity(), new Observer<List<DatabaseRestaurant>>() {
            @Override
            public void onChanged(@Nullable List<DatabaseRestaurant> restaurants) {
                adapter.addItems(restaurants);
            }
        });

        return v;
    }

    @Override
    public boolean onLongClick(View view) {
        DatabaseRestaurant databaseRestaurant = (DatabaseRestaurant) view.getTag();
        viewModel.deleteRestaurant(databaseRestaurant);
        return true;
    }
}
