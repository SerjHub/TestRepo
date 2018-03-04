package com.developer.starasov.githubclient.helpers;

import android.app.Fragment;

import com.developer.starasov.githubclient.activities.LoginActivity;
import com.developer.starasov.githubclient.models.AccountModel;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by macbook on 28.02.2018.
 */

public class GlobalValues {
    public static String PACKAGE_NAME = "com.developer.starasov.githubclient";


    public static JsonObject accountJsonData;
    public static AccountModel accountModel;
    public static String repoPathForDetail;


    //Loading states
    public static final int LOADED = 0;
    public static final int LOADING = 0;
    public static final int NOT_LOADED = 0;

}
