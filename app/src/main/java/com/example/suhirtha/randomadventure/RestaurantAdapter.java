package com.example.suhirtha.randomadventure;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.suhirtha.randomadventure.models.Restaurant;

import java.util.List;

/**
 * Created by anitac on 7/31/18.
 */

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder>{
    private List<Restaurant> mRestaurants;
    private DatabaseHelper db;
    public RestaurantAdapter(List<Restaurant> restaurants){
        mRestaurants = restaurants;
    }
    private Cursor mCursor;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context mContext = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View restaurantView = inflater.inflate(R.layout.item_restaurant,viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(restaurantView); //same as Dropholder holder = new Dropholder(restaurantview)
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewholder, int i) {
        mCursor.moveToPosition(i);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView restaurantName;

        public ViewHolder(View itemView){
            super(itemView);
            restaurantName = itemView.findViewById(R.id.nameRestaurant);
        }
    }


}
