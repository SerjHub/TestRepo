package com.developer.starasov.githubclient.helpers;

import android.app.Application;
import android.util.Log;

import com.developer.starasov.githubclient.Api.HttpClient;

import okhttp3.OkHttpClient;

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
