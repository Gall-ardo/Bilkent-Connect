package com.hao.bilkentconnect.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hao.bilkentconnect.ModelClasses.Chat;
import com.hao.bilkentconnect.ModelClasses.ChatMessage;
import com.hao.bilkentconnect.ModelClasses.User;
import com.hao.bilkentconnect.databinding.RecyclerChatBinding;
import com.hao.bilkentconnect.databinding.RecyclerChatMessageBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ChatMessageViewHolder>{

    private ArrayList<ChatMessage> chatMessages;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ChatMessageAdapter(ArrayList<ChatMessage> chatMessages)  {
        this.chatMessages = chatMessages;
    }



    @NonNull
    @Override
    public ChatMessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerChatMessageBinding chatsBinding = RecyclerChatMessageBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ChatMessageViewHolder(chatsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatMessageViewHolder holder, int position) {
        ChatMessage chatMessage = chatMessages.get(position);
        String messageText = chatMessage.getText();
        String senderId = chatMessage.getSenderId();

        holder.chatMessageBinding.messageText.setText(messageText);

        holder.chatMessageBinding.messageText.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);

        db.collection("Users").document(senderId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                User user = documentSnapshot.toObject(User.class);
                String url = user.getProfilePhoto();

                holder.chatMessageBinding.usernameText.setText(user.getUsername());

                Picasso.get().load(url).into(holder.chatMessageBinding.profilePicture);


            }
        });


    }

    @Override
    public int getItemCount() {
        return chatMessages.size();
    }

    public class ChatMessageViewHolder extends RecyclerView.ViewHolder{
        public RecyclerChatMessageBinding chatMessageBinding;
        public ChatMessageViewHolder(RecyclerChatMessageBinding chatsBinding) {
            super(chatsBinding.getRoot());
            this.chatMessageBinding = chatsBinding;
        }
    }
}