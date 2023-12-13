package com.hao.bilkentconnect.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hao.bilkentconnect.ModelClasses.Post;
import com.hao.bilkentconnect.OnPostClickListener;
import com.hao.bilkentconnect.databinding.RecyclerPostBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private ArrayList<Post> posts;

    private OnPostClickListener listener;

    public PostAdapter(ArrayList<Post> posts, OnPostClickListener listener) {
        this.posts = posts;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerPostBinding binding = RecyclerPostBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PostViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        holder.binding.descriptionText.setText(posts.get(position).postDescription);
        holder.binding.topUsernameText.setText(posts.get(position).sharerId);
        Picasso.get().load(posts.get(position).photoUrl).into(holder.binding.postImage);

        holder.binding.commentButton.setOnClickListener(v -> listener.onPostClick(posts.get(position)));

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        public RecyclerPostBinding binding;
        public PostViewHolder(RecyclerPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }



}
