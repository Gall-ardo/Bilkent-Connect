package com.hao.bilkentconnect.Adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hao.bilkentconnect.ModelClasses.Chat;
import com.hao.bilkentconnect.ModelClasses.User;
import com.hao.bilkentconnect.OnChatClickListener;
import com.hao.bilkentconnect.R;
import com.hao.bilkentconnect.databinding.RecyclerChatBinding;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder>{

    private ArrayList<Chat> chats;
    private OnChatClickListener listener;

    public ChatAdapter(ArrayList<Chat> chats, OnChatClickListener listener) {
        this.chats = chats;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerChatBinding chatsBinding = RecyclerChatBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ChatViewHolder(chatsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        /*Chat currentChat = chats.get(position);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String currentUserId = firebaseAuth.getCurrentUser() != null ? firebaseAuth.getCurrentUser().getUid() : "";
        String otherUserId = currentChat.getUsers().contains(currentUserId) ?
                currentChat.getUsers().stream().filter(id -> !id.equals(currentUserId)).findFirst().orElse("") :
                "";

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users").document(otherUserId).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                User user = documentSnapshot.toObject(User.class);
                if (user != null) {
                    holder.chatsBinding.usernameText.setText(user.getUsername());
                    if (user.getProfilePhoto() != null && !user.getProfilePhoto().isEmpty()) {
                        Picasso.get().load(user.getProfilePhoto()).into(holder.chatsBinding.userPhoto);
                    } else {
                        holder.chatsBinding.userPhoto.setImageResource(R.drawable.profile_icon); // Replace with your default image resource
                    }
                }
            }
        });

        holder.chatsBinding.usernameText.setOnClickListener((v -> listener.onChatClick(currentChat)));
        holder.chatsBinding.userPhoto.setOnClickListener((v -> listener.onChatClick(currentChat)));*/
    }


    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder{
        public RecyclerChatBinding chatsBinding;
        public ChatViewHolder(RecyclerChatBinding chatsBinding) {
            super(chatsBinding.getRoot());
            this.chatsBinding = chatsBinding;
        }
    }
}
