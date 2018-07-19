package com.example.suhirtha.randomadventure.models;

import org.parceler.Parcel;
/**
 * Created by togata on 7/19/18.
 */

@Parcel
public class Restaurant {
    private String name;
    private String id;

    public Restaurant() {

    }

    public Restaurant(String id, String name){
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
