package com.example.suhirtha.randomadventure.fragments;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Info;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.example.suhirtha.randomadventure.AppDatabase;
import com.example.suhirtha.randomadventure.R;
import com.example.suhirtha.randomadventure.activities.RestaurantDetailActivity;
import com.example.suhirtha.randomadventure.activities.SelectionActivity;
import com.example.suhirtha.randomadventure.models.DatabaseRestaurant;
import com.example.suhirtha.randomadventure.models.ResultActivityModel;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by togata on 7/27/18.
 */

public class ResultFragment extends Fragment implements OnMapReadyCallback{

    View view;
    private Context context;
    private TextView mName;
    private RatingBar mRating;
    private TextView mAddress;
    private TextView mPhoneNumber;
    private TextView mPrice;
    private CheckBox mDelivery;
    private CheckBox mReservation;
    private MapView mMap;
    public GoogleMap googleMap;
    private TextView mDistance;
    private TextView mDuration;
    private ImageButton mWalking;
    private ImageButton mDriving;
    private ResultActivityListener activityListener;
    public ResultActivityModel resultActivityModel;
    private List<DatabaseRestaurant> restaurants; //ADDED
    DatabaseRestaurant mRestaurant;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstances){
        view = inflater.inflate(R.layout.activity_result, container, false);
        setRetainInstance(true);

        mName = (TextView) view.findViewById(R.id.rsaName);
        mAddress = (TextView) view.findViewById(R.id.rsaAddress);
        mRating = (RatingBar) view.findViewById(R.id.rdaRating);
        mPhoneNumber = (TextView) view.findViewById(R.id.rsaPhoneNumber);
        mReservation = (CheckBox) view.findViewById(R.id.rsaReservation);
        mReservation.setClickable(false);
        mDelivery = (CheckBox) view.findViewById(R.id.rsaDelivery);
        mDelivery.setClickable(false);
        mMap = (MapView) view.findViewById(R.id.rsaMap);
        mMap.onCreate(savedInstances);
        mMap.getMapAsync(this);
        mDistance = (TextView) view.findViewById(R.id.rsaDistance);
        mDuration = (TextView) view.findViewById(R.id.rsaDuration);
        mWalking = (ImageButton) view.findViewById(R.id.rsaWalking);
        mDriving = (ImageButton) view.findViewById(R.id.rsaDriving);
        mPrice = (TextView) view.findViewById(R.id.rsaPrice);

        restaurants = new ArrayList<DatabaseRestaurant>(); //ADDED

        ImageButton mReset = (ImageButton) view.findViewById(R.id.rsaRedo);
        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SelectionActivity.class);
                startActivity(intent);
            }
        });

        ImageButton mAddToDatabase = (ImageButton) view.findViewById(R.id.rsaAdd);
        mAddToDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase db = Room.databaseBuilder(context, AppDatabase.class, "saved_restaurants")
                        .allowMainThreadQueries()
                        .build();
                db.restaurantDao().insertAll(new DatabaseRestaurant(resultActivityModel.getId(), resultActivityModel.getName(), resultActivityModel.getAddress(), 0.0 ," ", resultActivityModel.getDestination().latitude, resultActivityModel.getDestination().longitude));
                Intent intent = new Intent(getActivity(), RestaurantDetailActivity.class);
                mRestaurant = db.restaurantDao().findLastRestaurant();
                intent.putExtra("details", Parcels.wrap(mRestaurant.getId()));
                startActivity(intent);
            }
        });

        context = view.getContext();
        activityListener = (ResultActivityListener) getActivity();
        resultActivityModel = (ResultActivityModel) getArguments().getSerializable("model");

        return view;
    }

    public void updateRestaurantInfo() {
        mName.setText(resultActivityModel.getName());
        mRating.setRating(resultActivityModel.getRating());
        mAddress.setText(resultActivityModel.getAddress());
        mPhoneNumber.setText(resultActivityModel.getPhoneNumber());
        mPrice.setText(resultActivityModel.getPrice());
        if (resultActivityModel.getDeliverySetting() == true) {
            mDelivery.setChecked(true);
        }
        if (resultActivityModel.getReservationSetting()== true) {
            mReservation.setChecked(true);
        }
    }

    public void updateRestaurantDirections(Direction directions){
        if (resultActivityModel.getTransportationMode().equals("walking")){
            mWalking.setBackgroundColor(getResources().getColor(R.color.clearPurple));
            mDriving.setBackgroundColor(getResources().getColor(R.color.clear));
        }
        else{
            mWalking.setBackgroundColor(getResources().getColor(R.color.clear));
            mDriving.setBackgroundColor(getResources().getColor(R.color.clearPurple));
        }
        googleMap.clear();
        Route route = directions.getRouteList().get(0);
        Leg leg = route.getLegList().get(0);
        ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();
        PolylineOptions polylineOptions = DirectionConverter.createPolyline(context, directionPositionList, 5, getResources().getColor(R.color.darkPurple));
        googleMap.addPolyline(polylineOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(resultActivityModel.getOrigin()));
        googleMap.addMarker(new MarkerOptions().position(resultActivityModel.getOrigin()));
        googleMap.addMarker(new MarkerOptions().position(resultActivityModel.getDestination()));
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(resultActivityModel.getOrigin());
        builder.include(resultActivityModel.getDestination());
        LatLngBounds bounds = builder.build();
        int padding = 60;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 900, 750, padding);
        googleMap.animateCamera(cameraUpdate);

        Info distanceInfo = leg.getDistance();
        Info durationInfo = leg.getDuration();
        mDistance.setText(distanceInfo.getText());
        mDuration.setText(durationInfo.getText());
    }

    public void populateUserInterface(Direction directions){
        updateRestaurantInfo();
        updateRestaurantDirections(directions);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        activityListener.beginMappingDirections();
    }

    @Override
    public void onResume() {
        super.onResume();
        mMap.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMap.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMap.onStop();
    }
    @Override
    public void onPause() {
        mMap.onPause();
        super.onPause();
    }
    @Override
    public void onDestroy() {
        mMap.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMap.onLowMemory();
    }

    public interface ResultActivityListener {
        public void beginMappingDirections();
    }

}
