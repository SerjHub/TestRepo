package com.developer.starasov.githubclient.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developer.starasov.githubclient.helpers.GlobalValues;
import com.developer.starasov.githubclient.R;
import com.developer.starasov.githubclient.activities.MainActivity;
import com.developer.starasov.githubclient.fragments.RepositoryFragment;
import com.developer.starasov.githubclient.models.AccountModel;
import com.developer.starasov.githubclient.models.RepositoryModel;

import java.util.ArrayList;

/**
 * Created by macbook on 28.02.2018.
 */

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ReposViewHolder> {
    private ArrayList<RepositoryModel>list;
    private MainActivity mActivity;

    public ReposAdapter(ArrayList<RepositoryModel> list, MainActivity activity){
        this.list = list;
        this.mActivity = activity;
    }


    @NonNull
    @Override
    public ReposViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_repos,parent,false);
        return new ReposViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReposViewHolder holder, int position) {
        RepositoryModel model = list.get(position);
        ReposViewHolder mHolder = holder;
        mHolder.title.setText(model.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ReposViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        public ReposViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.repo_title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GlobalValues.repoPathForDetail = list.get(getAdapterPosition()).getName();
                    Log.i("FindStart",list.get(getAdapterPosition()).getName());
                    mActivity.showFragment(new RepositoryFragment());
                }
            });
        }
    }
}


