package com.hao.bilkentconnect.Adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hao.bilkentconnect.ModelClasses.Chat;
import com.hao.bilkentconnect.databinding.RecyclerChatBinding;


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
        holder.chatsBinding.usernameText.setText("Xia055");
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
