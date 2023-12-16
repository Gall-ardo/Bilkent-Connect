package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hao.bilkentconnect.Adapter.ChatAdapter;
import com.hao.bilkentconnect.ModelClasses.Chat;
import com.hao.bilkentconnect.ModelClasses.User;
import com.hao.bilkentconnect.databinding.ActivityChatAddBinding;
import com.hao.bilkentconnect.databinding.ActivityChatBinding;

import java.util.ArrayList;

public class ChatAddActivity extends AppCompatActivity implements OnChatClickListener {

    private ChatAdapter chatAdapter;
    private ArrayList<Chat> chatArrayList;
    private ActivityChatAddBinding binding;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    String currentUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatAddBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        chatArrayList = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        loadFriendsFromFirebase();


        binding.recyclerAddChatView.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(chatArrayList, this);
        binding.recyclerAddChatView.setAdapter(chatAdapter);
    }


    private void loadFriendsFromFirebase() {
        db.collection("Users").document(currentUserId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        User user = documentSnapshot.toObject(User.class);
                        if (user != null && user.getFriendIds() != null) {
                            for (String friendId : user.getFriendIds()) {
                                Chat chat = new Chat();
                                chat.setUser1(currentUserId);
                                chat.setUser2(friendId);
                                chatArrayList.add(chat);
                            }
                            chatAdapter.notifyDataSetChanged();
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Error loading friends", Toast.LENGTH_SHORT).show();
                });
    }


    @Override
    public void onChatClick(Chat chat) {
        startActivity(new Intent(ChatAddActivity.this, InnerChat.class));
        finish();
    }
}