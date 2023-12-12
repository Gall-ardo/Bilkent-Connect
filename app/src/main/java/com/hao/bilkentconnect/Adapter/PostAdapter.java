package com.hao.bilkentconnect.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hao.bilkentconnect.ModelClasses.Post;
import com.hao.bilkentconnect.databinding.RecyclerPostBinding;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private ArrayList<Post> posts;

    public PostAdapter(ArrayList<Post> posts) {
        this.posts = posts;
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
        //holder.binding.profilePicture.setImageURI();
        //        Picasso.get().load(postArrayList.get(position).downloadUrl).into(holder.recyclerRowBinding.recyclerviewRowImageview);
        //holder.binding.postImage.;

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
