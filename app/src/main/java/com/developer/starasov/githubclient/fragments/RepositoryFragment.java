package com.developer.starasov.githubclient.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.developer.starasov.githubclient.activities.MainActivity;
import com.developer.starasov.githubclient.adapters.CommitAdapter;
import com.developer.starasov.githubclient.api.ApiListener;
import com.developer.starasov.githubclient.helpers.ApplicationController;
import com.developer.starasov.githubclient.helpers.GlobalValues;
import com.developer.starasov.githubclient.R;
import com.developer.starasov.githubclient.models.CommitBodyModel;
import com.developer.starasov.githubclient.models.CommitModel;
import com.developer.starasov.githubclient.models.RepositoryModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by macbook on 28.02.2018.
 */

public class RepositoryFragment extends Fragment implements ApiListener {
    private ArrayList<CommitModel> mList;
    private MainActivity mActivity;
    private TextView ownerLogin, repoDescription;
    private FrameLayout errorLayout, loadingLayout, empty_repo;
    private RepositoryModel mRepositoryModel;
    private ImageView ownerAvatar;
    RecyclerView commitsRec;
    private String repositoryName;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.repo_card, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (MainActivity) getActivity();
        ownerLogin = getView().findViewById(R.id.repo_main_owner_login);
        repoDescription = getView().findViewById(R.id.repo_main_descr);
        errorLayout = getView().findViewById(R.id.repo_main_error);
        loadingLayout = getView().findViewById(R.id.repo_main_loading);
        ownerAvatar = getView().findViewById(R.id.repo_main_avatar_owner);
        empty_repo = getView().findViewById(R.id.empty_commit_fl);



        //list
        commitsRec = getView().findViewById(R.id.commits_recV);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        commitsRec.setLayoutManager(manager);
        commitsRec.addItemDecoration(new DividerItemDecoration(getActivity(),manager.getOrientation()));
        mList = new ArrayList<>();
        commitsRec.setAdapter(new CommitAdapter(mList));


        initFragment();
        loadRepository();

    }

    private void initFragment() {
        loadingLayout.setVisibility(View.VISIBLE);
        errorLayout.setVisibility(View.GONE);
        empty_repo.setVisibility(View.GONE);
        repositoryName = GlobalValues.repoPathForDetail;
        mActivity.setTitle(repositoryName);
    }

    private void fillData() {
        ownerLogin.setText("Owner: \n" + mRepositoryModel.getOwner().get("login").getAsString());
        Picasso.with(getActivity()).load(mRepositoryModel.getOwner().get("avatar_url").getAsString()).into(ownerAvatar);

    }


    private void loadRepository() {
        ApplicationController.getHttpClient().getRepositoryInfo(repositoryName, this);
        ApplicationController.getHttpClient().getCommits(repositoryName,new ApiListener() {
            @Override
            public void onResponse(JsonElement data, int code) {

                Log.i("FindCommits", String.valueOf(code) + ":::" + data.toString());

                switch (code) {
                    case 200: {
                        final JsonArray jsonArray = data.getAsJsonArray();
                        final Gson gson = new Gson();
                        for (int i = 0; i < jsonArray.size(); i++) {
                            CommitModel model = gson.fromJson(jsonArray.get(i),CommitModel.class);
                            model.parseCommitBody();
                            mList.add((gson.fromJson(jsonArray.get(i), CommitModel.class)).parseCommitBody());

                        }

                        commitsRec.getAdapter().notifyDataSetChanged();
                        break;
                    }
                    case 409: {
                        empty_repo.setVisibility(View.VISIBLE);
                        break;
                    }
                }
                loadingLayout.setVisibility(View.GONE);
            }

            @Override
            public void onError(Throwable t) {

            }
        });
    }


    @Override
    public void onResponse(JsonElement data, int code) {
        if (code == 200) {
            loadingLayout.setVisibility(View.GONE);
            mRepositoryModel = new Gson().fromJson(data, RepositoryModel.class);
            fillData();
        }

    }

    @Override
    public void onError(Throwable t) {
        errorLayout.setVisibility(View.VISIBLE);
        loadingLayout.setVisibility(View.GONE);
    }
}
