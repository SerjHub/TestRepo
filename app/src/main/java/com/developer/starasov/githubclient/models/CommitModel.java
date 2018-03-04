package com.developer.starasov.githubclient.models;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Created by macbook on 02.03.2018.
 */

public class CommitModel {

    //короткий commit message, автор, дата
    private String sha;
    private CommitBodyModel commitBodyModel;
    private JsonObject commit;
    private String date;

    public String getSha() {
        return sha;
    }

    public CommitBodyModel getCommit() {
        return commitBodyModel;
    }

    public String getDate() {
        return date;
    }

    public void setCommit(CommitBodyModel commit){
        this.commitBodyModel = commit;
    }

    public CommitModel parseCommitBody (){
        commitBodyModel = new Gson().fromJson(commit,CommitBodyModel.class);
        return this;
    }
}
