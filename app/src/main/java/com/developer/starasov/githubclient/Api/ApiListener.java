package com.developer.starasov.githubclient.Api;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by macbook on 28.02.2018.
 */

public interface ApiListener {
    void onResponse(JsonElement data, int code);
    void onError(Throwable t);
}
