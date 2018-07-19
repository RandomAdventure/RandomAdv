package com.example.suhirtha.randomadventure.models;

/**
 * Created by togata on 7/19/18.
 */

public class Restaurant {
    private String name;
    private String id;

    public Restaurant(String name, String id){
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
