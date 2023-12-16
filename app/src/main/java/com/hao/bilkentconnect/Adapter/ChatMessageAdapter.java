package com.hao.bilkentconnect.Adapter;

import android.view.LayoutInflater;
import android.view.View;
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
        ChatMessage chatMessage = chatMessages.get(position);
        String messageText = chatMessage.getText();
        String senderId = chatMessage.getSenderId();
        String receiverId = chatMessage.getReceiverId();

        holder.chatMessageBinding.messageText.setText(messageText);

        if (chatMessage.getSenderId().equals(senderId)) {
            // Apply styling for a sent message (e.g., aligning text to the right)
            holder.chatMessageBinding.messageText.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
            // You can also change background, text color, etc.
        } else {
            // Apply styling for a received message (e.g., aligning text to the left)
            holder.chatMessageBinding.messageText.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
            // Additional styling for received messages
        }

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