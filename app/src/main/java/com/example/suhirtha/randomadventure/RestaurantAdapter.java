package com.example.suhirtha.randomadventure;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView restaurantName;

        public ViewHolder(View itemView){
            super(itemView);
            restaurantName = itemView.findViewById(R.id.nameRestaurant);
        }
    }


}
