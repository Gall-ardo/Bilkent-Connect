package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.hao.bilkentconnect.Adapter.ChatMessageAdapter;
import com.hao.bilkentconnect.Adapter.CommentAdapter;
import com.hao.bilkentconnect.ModelClasses.Chat;
import com.hao.bilkentconnect.ModelClasses.ChatMessage;

import com.hao.bilkentconnect.ModelClasses.User;
import com.hao.bilkentconnect.databinding.ActivityInnerChatBinding;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InnerChat extends AppCompatActivity {
    private ActivityInnerChatBinding binding;
    private FirebaseFirestore db;
    private String currentUserId;
    private String otherUserId;
    private String chatId;
    private ChatMessageAdapter chatMessageAdapter;
    private ArrayList<ChatMessage> chatMessageArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInnerChatBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        db = FirebaseFirestore.getInstance();
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        otherUserId = getIntent().getStringExtra("otherUserId");

        chatMessageArrayList = new ArrayList<>();
        chatMessageAdapter = new ChatMessageAdapter(chatMessageArrayList);
        binding.recyclerChatMessageView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerChatMessageView.setAdapter(chatMessageAdapter);

        if (otherUserId != null) {
            loadOtherUserDetails(otherUserId);
            checkOrCreateChat(currentUserId, otherUserId, (chatId) -> {
                this.chatId = chatId;
                loadChatMessages(chatId);
            });
        }
    }

    private void loadOtherUserDetails(String userId) {
        db.collection("Users").document(userId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        User otherUser = documentSnapshot.toObject(User.class);
                        if (otherUser != null) {
                            // Assuming you have TextView for username and ImageView for profile in your layout
                            binding.FriendNameText.setText(otherUser.getUsername());
                            if (otherUser.getProfilePhoto() != null && !otherUser.getProfilePhoto().isEmpty()) {
                                Picasso.get().load(otherUser.getProfilePhoto()).into(binding.profilePicture);
                            }
                        }
                    }
                })
                .addOnFailureListener(e -> Log.e("InnerChat", "Error loading user details", e));
    }
    private void checkOrCreateChat(String currentUserId, String otherUserId, OnChatIdGeneratedListener callback) {
        db.collection("Chats")
                .whereArrayContains("users", Arrays.asList(currentUserId, otherUserId))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (!querySnapshot.isEmpty()) {
                            chatId = querySnapshot.getDocuments().get(0).getId();
                            callback.onChatIdGenerated(chatId);
                        } else {
                            createChat(currentUserId, otherUserId, callback);
                        }
                    } else {
                        Log.e("InnerChat", "Error checking or creating chat", task.getException());
                    }
                });
    }
    private void loadChatMessages(String chatId) {
        db.collection("Chats").document(chatId).collection("Messages")
                .orderBy("timestamp", Query.Direction.ASCENDING)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        Log.e("InnerChat", "Error loading messages: ", e);
                        return;
                    }

                    chatMessageArrayList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                        ChatMessage message = doc.toObject(ChatMessage.class);
                        chatMessageArrayList.add(message);
                    }
                    chatMessageAdapter.notifyDataSetChanged();
                });
    }

    public void sendMessage(View view) {
        String messageText = binding.directMessageText.getText().toString().trim();
        if (messageText.isEmpty()) {
            Toast.makeText(this, "Message cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        ChatMessage message = new ChatMessage(currentUserId, otherUserId, messageText, chatId, new Timestamp(new Date()));
        db.collection("Chats").document(chatId).collection("Messages").add(message)
                .addOnSuccessListener(documentReference -> {
                    updateChatLastMessage(chatId, messageText);
                    binding.directMessageText.setText("");
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error sending message: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void updateChatLastMessage(String chatId, String lastMessage) {
        Map<String, Object> updates = new HashMap<>();
        updates.put("lastMessage", lastMessage);
        updates.put("lastActivityTime", new Timestamp(new Date()));

        db.collection("Chats").document(chatId).update(updates)
                .addOnFailureListener(e -> Log.e("InnerChat", "Failed to update chat: " + e.getMessage()));
    }



    private void createChat(String currentUserId, String otherUserId, OnChatIdGeneratedListener callback) {
        Map<String, Object> newChatData = new HashMap<>();
        newChatData.put("users", Arrays.asList(currentUserId, otherUserId));
        db.collection("Chats").add(newChatData)
                .addOnSuccessListener(documentReference -> {
                    chatId = documentReference.getId();
                    callback.onChatIdGenerated(chatId);
                })
                .addOnFailureListener(e -> Log.e("InnerChat", "Error creating new chat", e));
    }


    public void goToProfilePage(View view) {
        startActivity(new Intent(InnerChat.this, ChatActivity.class));
        finish();
    }



    public interface OnChatIdGeneratedListener {
        void onChatIdGenerated(String chatId);
    }
}