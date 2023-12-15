package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.hao.bilkentconnect.Adapter.ChatAdapter;
import com.hao.bilkentconnect.ModelClasses.Chat;
import com.hao.bilkentconnect.databinding.ActivityChatAddBinding;
import com.hao.bilkentconnect.databinding.ActivityChatBinding;

import java.util.ArrayList;

public class ChatAddActivity extends AppCompatActivity {

    private ChatAdapter chatAdapter;
    private ArrayList<Chat> chatArrayList;
    private ActivityChatAddBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatAddBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        chatArrayList = new ArrayList<>();
        //chatArrayList.add(new Chat(22203359, 2000000));
        //chatArrayList.add(new Chat(22203359, 2000000));
        //chatArrayList.add(new Chat(22203359, 2000000));
        //chatArrayList.add(new Chat(22203359, 2000000));


        binding.recyclerAddChatView.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(chatArrayList);
        binding.recyclerAddChatView.setAdapter(chatAdapter);
    }
}