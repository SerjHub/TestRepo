package com.developer.starasov.githubclient.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developer.starasov.githubclient.R;
import com.developer.starasov.githubclient.models.CommitBodyModel;
import com.developer.starasov.githubclient.models.CommitModel;

import java.util.ArrayList;

/**
 * Created by macbook on 04.03.2018.
 */

public class CommitAdapter extends RecyclerView.Adapter<CommitAdapter.CommitViewHolder> {
    private ArrayList<CommitModel>list;

    public CommitAdapter (ArrayList<CommitModel> list){
        this.list = list;
    }


    @NonNull
    @Override
    public CommitAdapter.CommitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.commit_cell,parent,false);
        return new CommitAdapter.CommitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommitAdapter.CommitViewHolder holder, int position) {
        CommitModel model = list.get(position);
        CommitBodyModel bodyModel = model.getCommit();
        holder.hash.setText(model.getSha());
        holder.message.setText(bodyModel.getMessage());
        holder.author.setText(bodyModel.getAuthor().get("name").getAsString());
        holder.date.setText(bodyModel.getAuthor().get("date").getAsString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CommitViewHolder extends RecyclerView.ViewHolder {
        TextView author,message,hash,date;
        public CommitViewHolder(View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.commit_author);
            message = itemView.findViewById(R.id.commit_message);
            hash = itemView.findViewById(R.id.commit_hash);
            date = itemView.findViewById(R.id.commit_date);
        }
    }
}
