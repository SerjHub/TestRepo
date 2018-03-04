package com.developer.starasov.githubclient.models;

import com.google.gson.JsonObject;

/**
 * Created by macbook on 04.03.2018.
 */

public class CommitBodyModel {
    private JsonObject author;
    private String message;

    public JsonObject getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }
}
