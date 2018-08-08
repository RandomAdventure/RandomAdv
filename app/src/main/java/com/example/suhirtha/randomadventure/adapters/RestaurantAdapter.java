package com.example.suhirtha.randomadventure.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.suhirtha.randomadventure.R;
import com.example.suhirtha.randomadventure.activities.RestaurantDetailActivity;
import com.example.suhirtha.randomadventure.models.DatabaseRestaurant;

import org.parceler.Parcels;

import java.util.List;

/**
 * Created by anitac on 7/31/18.
 */


public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder>{
    private List<DatabaseRestaurant> mRestaurants;
    Context context;
    private View.OnLongClickListener longClickListener;



    public RestaurantAdapter(Context context, List<DatabaseRestaurant> restaurants, View.OnLongClickListener longClickListener){
        mRestaurants = restaurants;
        this.context= context;
        this.longClickListener = longClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_restaurant, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewholder, int i) {
        DatabaseRestaurant databaseRestaurant = mRestaurants.get(i);
        viewholder.restaurantName.setText(mRestaurants.get(i).getRestaurantName());
        viewholder.restaurantRating.setText(mRestaurants.get(i).getRating() + "");
        viewholder.restaurantAdress.setText(mRestaurants.get(i).getAddress());
        viewholder.itemView.setOnLongClickListener(longClickListener);
    }

    @Override
    public int getItemCount() {
        return mRestaurants.size();
    }

    public void addItems(List<DatabaseRestaurant> mRestaurants) {
        this.mRestaurants = mRestaurants;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView restaurantName;
        public TextView restaurantAdress;
        public TextView restaurantRating;
        public double ratingValue;

        public ViewHolder(View itemView){
            super(itemView);
            restaurantName = itemView.findViewById(R.id.nameRestaurant);
            restaurantAdress = itemView.findViewById(R.id.irAddress);
            restaurantRating = itemView.findViewById(R.id.irRating);
            ratingValue = Double.parseDouble(restaurantRating.getText().toString());
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            if (position != RecyclerView.NO_POSITION) {
                DatabaseRestaurant restaurant = mRestaurants.get(position);

                Intent i = new Intent(context, RestaurantDetailActivity.class);
                i.putExtra("details", Parcels.wrap(position + 1));
                context.startActivity(i);

//                //do this in the background, wrap in background
//                //need this to insert data
//                AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "saved_restaurants")
//                        .allowMainThreadQueries() //TODO change this
//                        .build();
//
//                db.restaurantDao().delete(restaurant);
            }
        }
    }



}
