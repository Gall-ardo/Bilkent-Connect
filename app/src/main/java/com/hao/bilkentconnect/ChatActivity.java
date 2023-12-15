package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.hao.bilkentconnect.Adapter.ChatAdapter;
import com.hao.bilkentconnect.Adapter.CommentAdapter;
import com.hao.bilkentconnect.ModelClasses.Chat;
import com.hao.bilkentconnect.databinding.ActivityChangePasswordPageBinding;
import com.hao.bilkentconnect.databinding.ActivityChatBinding;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {
    private ActivityChatBinding binding;
    private ArrayList<Chat> chatArrayList;
    private ChatAdapter chatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        chatArrayList = new ArrayList<>();
        chatArrayList.add(new Chat(22203359, 2000000));
        chatArrayList.add(new Chat(22203359, 2000000));
        chatArrayList.add(new Chat(22203359, 2000000));
        chatArrayList.add(new Chat(22203359, 2000000));


        binding.recyclerChatView.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(chatArrayList);
        binding.recyclerChatView.setAdapter(chatAdapter);

    }
    public void goToMainPageChatActivity(View view){
        Intent intent = new Intent(ChatActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}