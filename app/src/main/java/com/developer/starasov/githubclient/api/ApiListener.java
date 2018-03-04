package com.developer.starasov.githubclient.api;

import com.google.gson.JsonElement;

/**
 * Created by macbook on 28.02.2018.
 */

public interface ApiListener {
    void onResponse(JsonElement data, int code);
    void onError(Throwable t);
}
