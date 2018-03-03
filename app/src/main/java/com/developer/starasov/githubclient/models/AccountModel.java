package com.developer.starasov.githubclient.models;

/**
 * Created by macbook on 28.02.2018.
 */

public class AccountModel {
//    private int loadState;

    private int id;

    private String login;
    private String avatar_url;
    private String ownerLogin;
    private String bio;
    private int forks;
    private int watches;

//    public int getLoadState() {
//        return loadState;
//    }

    public String getLogin() {
        return login;
    }


    public String getAvatar() {
        return avatar_url;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public int getId() {
        return id;
    }

        public int getForks() {
        return forks;
    }

    public int getWatches() {
        return watches;
    }

    public String getBio(){return bio;}
}
