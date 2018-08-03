package com.example.suhirtha.randomadventure;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.suhirtha.randomadventure.models.DatabaseRestaurant;

import java.util.List;

/**
 * Created by anitac on 7/31/18.
 */


public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder>{
    private List<DatabaseRestaurant> mRestaurants;

    public RestaurantAdapter(List<DatabaseRestaurant> restaurants){
        mRestaurants = restaurants;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_restaurant, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewholder, int i) {
        viewholder.restaurantName.setText(mRestaurants.get(i).getRestaurantName());
    }

    @Override
    public int getItemCount() {
        return mRestaurants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView restaurantName;

        public ViewHolder(View itemView){
            super(itemView);
            restaurantName = itemView.findViewById(R.id.nameRestaurant);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                DatabaseRestaurant restaurant = mRestaurants.get(position);
                Log.d("RestaurantAdapter", "Deleting database at position: " + position);
            }

            //do this in the background, wrap in background
            //need this to insert data
//            DatabaseHelper db = Room.databaseBuilder(this, DatabaseHelper.class, "saved_restaurants")
//                    .allowMainThreadQueries() //TODO change this
//                    .build();
//
//            db.restaurantDao().delete();
        }
    }



}
