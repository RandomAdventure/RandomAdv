package com.example.suhirtha.randomadventure.models;

/**
 * Created by togata on 7/19/18.
 */

public class Restaurant {
    private String name;
    private String id;

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
