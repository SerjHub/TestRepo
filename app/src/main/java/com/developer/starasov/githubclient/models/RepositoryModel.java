package com.developer.starasov.githubclient.models;

import com.google.gson.JsonObject;

/**
 * Created by macbook on 28.02.2018.
 */

public class RepositoryModel {
    private int id;
    private String name;
    private JsonObject owner;
    private String description;
    private int forks;
    private int watches;

//    private String hash;
//    private String message;
//    private String author;
//    private String date;


    public String getName() {
        return name;
    }
    public JsonObject getOwner() {
        return owner;
    }
}
