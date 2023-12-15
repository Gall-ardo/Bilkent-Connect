package com.hao.bilkentconnect.Adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.hao.bilkentconnect.ModelClasses.Chat;
import com.hao.bilkentconnect.ModelClasses.User;
import com.hao.bilkentconnect.R;
import com.hao.bilkentconnect.databinding.RecyclerChatBinding;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder>{

    private ArrayList<Chat> chats;

    public ChatAdapter(ArrayList<Chat> chats)  {
        this.chats = chats;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerChatBinding chatsBinding = RecyclerChatBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ChatViewHolder(chatsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {

        Chat currentChat = chats.get(position);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = currentChat.getUser2();

        // Fetch user by ID to get the username
        db.collection("Users").document(userId).get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()){

                User user = documentSnapshot.toObject(User.class);
                if (user != null){

                    holder.chatsBinding.usernameText.setText(user.getUsername());
                    if (user.getProfilePhoto() != null){
                        Picasso.get().load(user.getProfilePhoto()).into(holder.chatsBinding.userPhoto);
                    }else{
                        holder.chatsBinding.userPhoto.setImageResource(R.drawable.circle);
                    }
                }

            }

        });

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
