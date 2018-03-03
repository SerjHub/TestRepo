package com.developer.starasov.githubclient.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.developer.starasov.githubclient.Api.ApiListener;
import com.developer.starasov.githubclient.activities.LoginActivity;
import com.developer.starasov.githubclient.helpers.ApplicationController;
import com.developer.starasov.githubclient.helpers.GlobalValues;
import com.developer.starasov.githubclient.R;
import com.developer.starasov.githubclient.activities.MainActivity;
import com.developer.starasov.githubclient.adapters.ReposAdapter;
import com.developer.starasov.githubclient.models.AccountModel;
import com.developer.starasov.githubclient.models.RepositoryModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by macbook on 28.02.2018.
 */

public class AccountFragment extends Fragment implements ApiListener {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ArrayList<RepositoryModel>list;
    private ReposAdapter mAdapter;
    private FrameLayout noConnectionFL;
    private AccountModel mAccount;
    private TextView accountTitle, exitBtn, forks, watches, description;
    private ImageView avatar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.account_fragment,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAccount = GlobalValues.accountModel;

        MainActivity activity = (MainActivity) getActivity();
        progressBar = getView().findViewById(R.id.repos_progress);
        recyclerView = getView().findViewById(R.id.repos_recycler);
        accountTitle = getView().findViewById(R.id.account_name);
        forks = getView().findViewById(R.id.account_forks);
        watches = getView().findViewById(R.id.account_watches);
        description = getView().findViewById(R.id.account_description);
        avatar = getView().findViewById(R.id.account_avatar);
        exitBtn = getView().findViewById(R.id.exit_account);
        noConnectionFL = getView().findViewById(R.id.no_connection_fl);
        noConnectionFL.setVisibility(View.GONE);

        //List settings
        recyclerView.setVisibility(View.GONE);
        LinearLayoutManager mManager = new LinearLayoutManager(activity);
        mManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mManager);
        list = new ArrayList<>();
        mAdapter = new ReposAdapter(list,activity);
        recyclerView.setAdapter(mAdapter);


        initListeners();
        //init data
        showAccountData();
        loadRepos();
    }


    private void showAccountData(){
        accountTitle.setText(mAccount.getLogin());
        description.setText(mAccount.getBio());
        forks.setText(getString(R.string.forks_title) + mAccount.getForks());
        watches.setText(getString(R.string.watches_title) + mAccount.getWatches());
        Picasso.with(getActivity()).load(GlobalValues.accountModel.getAvatar()).into(avatar);
    }

    private void initListeners(){
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getActivity().getPreferences(Context.MODE_PRIVATE);
                preferences.edit()
                        .remove("login")
                        .remove("password")
                        .apply();
                getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden){
            showAccountData();
            loadRepos();
        }
    }

    private void loadRepos(){
        progressBar.setVisibility(View.VISIBLE);
        String path = GlobalValues.accountModel.getLogin();
        ApplicationController.getHttpClient().getRepos(path,this);
    }


    @Override
    public void onResponse(JsonElement data, int code) {

        Log.i("AccountResponse",data.toString());
        if (code == 200 ){
            JsonArray jsonArray = data.getAsJsonArray();
            for (int i = 0; i < jsonArray.size(); i++){
                RepositoryModel model = new Gson().fromJson(jsonArray.get(i).getAsJsonObject(),RepositoryModel.class);
                list.add(model);
            }

            mAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            noConnectionFL.setVisibility(View.GONE);

        }
    }

    @Override
    public void onError(Throwable t) {
        progressBar.setVisibility(View.GONE);
        noConnectionFL.setVisibility(View.VISIBLE);
    }

}
