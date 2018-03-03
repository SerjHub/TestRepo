package com.developer.starasov.githubclient.Api;

import android.util.Base64;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;


/**
 * Created by macbook on 27.02.2018.
 */

public class HttpClient  {
    private OkHttpClient okHttpClient;
    private String BASE_URL = "https://api.github.com/";
    private String log_pass_enc;
    private String login_for_path;


    public HttpClient(){
        okHttpClient = new OkHttpClient.Builder().build();
    }


    private Request createRequest(String authData, String path){
        Log.i("FindData",BASE_URL + path);
        Request request = new Request.Builder()
                .url(BASE_URL + path)
                .addHeader("Authorization", "Basic "+ authData)
                .get()
                .build();
        return request;
    }

    public void loginGitHub(String username, String pass, ApiListener listener){
        String encode = Base64.encodeToString((username + ":" + pass).getBytes(), Base64.DEFAULT).replace("\n", "");
        login_for_path = username;
        log_pass_enc = encode;
        okHttpClient.newCall(createRequest(encode, "user")).enqueue(new ApiCallbackWorker(listener) {});
    }

    public void getRepos(String path,ApiListener listener){
        okHttpClient.newCall(createRequest(log_pass_enc,"users/" + path + "/repos")).enqueue(new ApiCallbackWorker(listener) {});
    }

    public void getRepositoryInfo(String repoName, ApiListener listener){
        okHttpClient.newCall(createRequest(log_pass_enc,"repos/" + login_for_path + "/" + repoName)).enqueue(new ApiCallbackWorker(listener) {});
    }

    public void getCommits(ApiListener listener){
        okHttpClient.newCall(createRequest(log_pass_enc,"repos/TestUserExample/TestRepo/commits")).enqueue(new ApiCallbackWorker(listener) {});
    }

}
