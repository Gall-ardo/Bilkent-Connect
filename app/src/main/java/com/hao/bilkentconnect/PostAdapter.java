package com.hao.bilkentconnect;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        // Define your ViewHolder views here
        public PostViewHolder(View itemView) {
            super(itemView);
            // this.titleTextView = itemView.findViewById(R.id.title);
        }
    }



}
