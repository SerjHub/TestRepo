package com.developer.starasov.githubclient.helpers;

import android.app.Application;

import com.developer.starasov.githubclient.api.HttpClient;

/**
 * Created by macbook on 27.02.2018.
 */

public class ApplicationController extends Application {
    private static HttpClient httpClient;

    @Override
    public void onCreate() {
        super.onCreate();
        httpClient = new HttpClient();
    }

    public static HttpClient getHttpClient(){ return httpClient; }
}
