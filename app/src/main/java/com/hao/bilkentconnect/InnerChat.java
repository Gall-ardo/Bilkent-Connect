package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.Timestamp;
import com.hao.bilkentconnect.Adapter.ChatMessageAdapter;
import com.hao.bilkentconnect.Adapter.CommentAdapter;
import com.hao.bilkentconnect.ModelClasses.ChatMessage;

import com.hao.bilkentconnect.ModelClasses.User;
import com.hao.bilkentconnect.databinding.ActivityInnerChatBinding;


import java.util.ArrayList;

public class InnerChat extends AppCompatActivity {
    private ChatMessageAdapter chatMessageAdapter;
    private ArrayList<ChatMessage> chatMessageArrayList;
    private ActivityInnerChatBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInnerChatBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        chatMessageArrayList = new ArrayList<>();
        chatMessageArrayList.add(new ChatMessage(222222, 222222222, "Hello"));
        chatMessageArrayList.add(new ChatMessage(22222222, 222222222, "Hi"));

        binding.recyclerChatMessageView.setLayoutManager(new LinearLayoutManager(this));
        chatMessageAdapter = new ChatMessageAdapter(chatMessageArrayList);
        binding.recyclerChatMessageView.setAdapter(chatMessageAdapter);


    }
}