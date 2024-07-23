package com.hao.bilkentconnect.Adapter;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hao.bilkentconnect.InnerChat;
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

    private FirebaseFirestore db;
    private FirebaseAuth myAuth;

    public ChatAdapter(ArrayList<Chat> chats) {
        this.chats = chats;

        db = FirebaseFirestore.getInstance();
        myAuth = FirebaseAuth.getInstance();

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

        String currentUserId = myAuth.getCurrentUser().getUid();

        String otherUserId = chats.get(position).getOthersId();


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


        if (currentChat.getUnreadMessageCount() == 0){
            holder.chatsBinding.unreadMessage.setVisibility(View.GONE);
        }

        else{
            holder.chatsBinding.unreadMessage.setVisibility(View.VISIBLE);
            holder.chatsBinding.unreadMessage.setText(currentChat.getUnreadMessageCount() + "");
        }



        //holder.chatsBinding.usernameText.setOnClickListener((v -> listener.onChatClick(currentChat)));
        //holder.chatsBinding.userPhoto.setOnClickListener((v -> listener.onChatClick(currentChat)));


        String otherId = chats.get(position).getOthersId();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), InnerChat.class);
                intent.putExtra("otherId", otherId);
                holder.itemView.getContext().startActivity(intent);
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
