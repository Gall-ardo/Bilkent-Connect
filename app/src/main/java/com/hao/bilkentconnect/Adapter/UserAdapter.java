package com.hao.bilkentconnect.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hao.bilkentconnect.ModelClasses.User;
import com.hao.bilkentconnect.OnUserClickListener;
import com.hao.bilkentconnect.R;
import com.hao.bilkentconnect.databinding.RecyclerUserBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private ArrayList<User> users;
    private OnUserClickListener listener;

    public UserAdapter(ArrayList<User> users, OnUserClickListener listener) {
        this.users = users;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerUserBinding userBinding = RecyclerUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UserViewHolder(userBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User currentUser = users.get(position);
        holder.userBinding.usernameText.setText(currentUser.getUsername());

        if (currentUser.getProfilePhoto() != null && !currentUser.getProfilePhoto().isEmpty()) {
            Picasso.get().load(currentUser.getProfilePhoto()).into(holder.userBinding.userPhoto);
        } else {
            holder.userBinding.userPhoto.setImageResource(R.drawable.profile_icon);
        }

        holder.itemView.setOnClickListener(v -> listener.onUserClick(currentUser));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        public RecyclerUserBinding userBinding;

        public UserViewHolder(RecyclerUserBinding userBinding) {
            super(userBinding.getRoot());
            this.userBinding = userBinding;
        }
    }
}
