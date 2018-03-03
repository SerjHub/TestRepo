package com.developer.starasov.githubclient.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.starasov.githubclient.Api.ApiListener;
import com.developer.starasov.githubclient.helpers.ApplicationController;
import com.developer.starasov.githubclient.helpers.GlobalValues;
import com.developer.starasov.githubclient.R;
import com.developer.starasov.githubclient.models.CommitModel;
import com.developer.starasov.githubclient.models.RepositoryModel;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by macbook on 28.02.2018.
 */

public class RepositoryFragment extends Fragment implements ApiListener {
    private ArrayList<CommitModel>mList;
    private ImageView avatar;
    private TextView ownerLogin,repoTitle,repoDescription,forks,watches;
    private FrameLayout errorLayout, loadingLayout;
    private RepositoryModel mRepositoryModel;
    private ImageView ownerAvatar;

    private String repositoryName;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.repo_card,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ownerLogin = getView().findViewById(R.id.repo_main_owner_login);
        repoTitle = getView().findViewById(R.id.repo_main_title);
        repoDescription = getView().findViewById(R.id.repo_main_descr);
        forks = getView().findViewById(R.id.forks);
        watches = getView().findViewById(R.id.watches);
        errorLayout = getView().findViewById(R.id.repo_main_error);
        loadingLayout = getView().findViewById(R.id.repo_main_loading);
        ownerAvatar = getView().findViewById(R.id.repo_main_avatar_owner);




        initFragment();
        loadRepository();

    }

    private void initFragment(){
        loadingLayout.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.GONE);
        repositoryName = GlobalValues.repoPathForDetail;
    }

    private void fillData(){
        ownerLogin.setText(mRepositoryModel.getOwner().get("login").getAsString());
        Picasso.with(getActivity()).load(mRepositoryModel.getOwner().get("avatar_url").getAsString()).into(ownerAvatar);

    }


    private void loadRepository(){
        ApplicationController.getHttpClient().getRepositoryInfo(repositoryName,this);
        ApplicationController.getHttpClient().getCommits(new ApiListener() {
            @Override
            public void onResponse(JsonElement data, int code) {

                Log.i("FindCommits",String.valueOf(code) + ":::" + data.toString());
            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }




    @Override
    public void onResponse(JsonElement data, int code) {
        Log.i("FindDetailRep",String.valueOf(code) + "---" + data.toString());
        if (code == 200){
            loadingLayout.setVisibility(View.GONE);
            mRepositoryModel = new Gson().fromJson(data,RepositoryModel.class);
            fillData();
        }

    }

    @Override
    public void onError(Throwable t) {
        errorLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
    }
}
