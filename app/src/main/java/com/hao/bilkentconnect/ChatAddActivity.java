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
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String currentUserId = firebaseAuth.getCurrentUser().getUid();

        db.collection("Users").document(currentUserId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Map<String, Object> userData = documentSnapshot.getData();
                        if (userData != null) {
                            List<String> friendIds = (List<String>) userData.get("friends");
                            if (friendIds != null) {
                                for (String friendId : friendIds) {
                                    Chat chat = new Chat(currentUserId, friendId);
                                    chatArrayList.add(chat);
                                }
                                chatAdapter.notifyDataSetChanged();
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Document does not exist", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Error loading friends: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }


    @Override
    public void onChatClick(Chat chat) {
        List<String> userIds = chat.getUsers();
        String otherUserId = userIds.stream().filter(id -> !id.equals(currentUserId)).findFirst().orElse(null);

        if (otherUserId == null) {
            Toast.makeText(this, "Error: Other user ID not found", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(ChatAddActivity.this, InnerChat.class);
        intent.putExtra("otherUserId", otherUserId);
        startActivity(intent);
        finish();
    }

}