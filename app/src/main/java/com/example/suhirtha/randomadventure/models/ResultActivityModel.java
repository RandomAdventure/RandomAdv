package com.example.suhirtha.randomadventure.models;

import com.google.android.gms.maps.model.LatLng;

import org.parceler.Parcel;

import java.io.Serializable;

/**
 * Created by togata on 8/2/18.
 */

@Parcel
public class ResultActivityModel implements Serializable{

    private transient LatLng origin;
    private transient LatLng destination;
    private String transportationMode;
    private String id;
    private String name;
    private String address;
    private float rating;
    private boolean deliverySetting;
    private boolean reservationSetting;
    private String phoneNumber;
    private String price;

    public ResultActivityModel(){}

    public ResultActivityModel(LatLng origin, LatLng destination, String transportationMode, String id, String name, String address, float rating, boolean deliverySetting, boolean reservationSetting, String phoneNumber, String price) {
        this.origin = origin;
        this.destination = destination;
        this.transportationMode = transportationMode;
        this.id = id;
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.deliverySetting = deliverySetting;
        this.reservationSetting = reservationSetting;
        this.phoneNumber = phoneNumber;
        this.price = price;
    }

    public LatLng getOrigin() {
        return origin;
    }

    public void setOrigin(LatLng origin) {
        this.origin = origin;
    }

    public LatLng getDestination() {
        return destination;
    }

    public void setDestination(LatLng destination) {
        this.destination = destination;
    }

    public String getTransportationMode() {
        return transportationMode;
    }

    public void setTransportationMode(String transportationMode) {
        this.transportationMode = transportationMode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public boolean getDeliverySetting() {
        return deliverySetting;
    }

    public void setDeliverySetting(boolean deliverySetting) {
        this.deliverySetting = deliverySetting;
    }

    public boolean getReservationSetting() {
        return reservationSetting;
    }

    public void setReservationSetting(boolean reservationSetting) {
        this.reservationSetting = reservationSetting;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }
}
