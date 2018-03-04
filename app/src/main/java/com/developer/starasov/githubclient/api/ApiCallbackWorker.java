package com.developer.starasov.githubclient.api;

import android.os.Handler;
import android.support.annotation.MainThread;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by macbook on 28.02.2018.
 */

abstract class ApiCallbackWorker implements Callback {
    private ApiListener listener;
    private static Handler handler;

    ApiCallbackWorker(ApiListener listener){
        this.listener = listener;
        if (handler == null) handler = new Handler();
    }

    @Override
    public void onFailure(Call call, IOException e) {
        listener.onError(e.fillInStackTrace());
    }

    @MainThread
    @Override
    public void onResponse(Call call,final Response response) throws IOException {
        JsonParser parser = new JsonParser();
        final JsonElement object = parser.parse(response.body().string());
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.onResponse(object,response.code());
            }
        });

    }
}
