package com.hao.bilkentconnect.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hao.bilkentconnect.ModelClasses.Chat;
import com.hao.bilkentconnect.ModelClasses.ChatMessage;
import com.hao.bilkentconnect.databinding.RecyclerChatBinding;
import com.hao.bilkentconnect.databinding.RecyclerChatMessageBinding;

import java.util.ArrayList;

public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ChatMessageViewHolder>{

    private ArrayList<ChatMessage> chatMessages;

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
        holder.chatMessageBinding.usernameText.setText("Xia055");
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