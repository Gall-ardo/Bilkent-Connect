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
    private ChatMessageAdapter chatMessageAdapter;
    private ArrayList<ChatMessage> chatMessageArrayList;
    private ActivityInnerChatBinding binding;
    String otherUserId;
    String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private boolean isChatChecked = false;
    private String chatId = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInnerChatBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        otherUserId = getIntent().getStringExtra("otherUserId");
        if (otherUserId == null) {
            Toast.makeText(this, "No user ID received", Toast.LENGTH_LONG).show();
            return;
        }
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        if (otherUserId == null) {
            Toast.makeText(this, "No user ID received", Toast.LENGTH_LONG).show();
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users").document(otherUserId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        User user = documentSnapshot.toObject(User.class);
                        if (user != null) {
                            binding.FriendNameText.setText(user.getUsername());
                            if (user.getProfilePhoto() != null && !user.getProfilePhoto().isEmpty()) {
                                Picasso.get().load(user.getProfilePhoto()).into(binding.profilePicture);
                            } else {
                                binding.profilePicture.setImageResource(R.drawable.profile_icon); // Replace with your default image resource
                            }
                        }
                    } else {
                        Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());

        if (!isChatChecked) {
            checkOrCreateChat(currentUserId, otherUserId, FirebaseFirestore.getInstance(), chatId -> {
                isChatChecked = true;
            });
        }


        chatMessageArrayList = new ArrayList<>();
        binding.recyclerChatMessageView.setLayoutManager(new LinearLayoutManager(this));
        chatMessageAdapter = new ChatMessageAdapter(chatMessageArrayList);
        binding.recyclerChatMessageView.setAdapter(chatMessageAdapter);
    }
    public void sendMessage(View view) {
        String messageText = binding.directMessageText.getText().toString();
        if (messageText.isEmpty()) {
            Toast.makeText(this, "Message cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if (chatId == null) {
            Toast.makeText(this, "Chat ID is not set", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        ChatMessage message = new ChatMessage(currentUserId, otherUserId, messageText, chatId, new Timestamp(new Date()));

        db.collection("Chats").document(chatId).collection("Messages").add(message)
                .addOnSuccessListener(documentReference -> {
                    chatMessageArrayList.add(message);
                    chatMessageAdapter.notifyDataSetChanged();
                    binding.directMessageText.setText("");

                    Map<String, Object> updates = new HashMap<>();
                    updates.put("lastMessage", messageText);
                    updates.put("lastActivityTime", new Timestamp(new Date()));

                    db.collection("Chats").document(chatId).update(updates)
                            .addOnFailureListener(e -> Log.e("Update Error", "Failed to update chat: " + e.getMessage()));

                    Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error sending message: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }



    private void checkOrCreateChat(String currentUserId, String otherUserId, FirebaseFirestore db, OnChatIdResolvedCallback callback) {
        Query query = db.collection("Chats")
                .whereArrayContains("users", Arrays.asList(currentUserId, otherUserId));

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<DocumentSnapshot> documents = task.getResult().getDocuments();
                if (!documents.isEmpty()) {
                    // Chat exists, use the existing chat ID
                    this.chatId = documents.get(0).getId();
                    callback.onChatIdResolved(this.chatId);
                } else {
                    // Create a new chat
                    Map<String, Object> newChatData = new HashMap<>();
                    newChatData.put("users", Arrays.asList(currentUserId, otherUserId));
                    db.collection("Chats").add(newChatData)
                            .addOnSuccessListener(documentReference -> {
                                this.chatId = documentReference.getId();
                                callback.onChatIdResolved(this.chatId);
                            });
                }
            } else {
                // Log or handle the error
                Log.e("ChatError", "Error checking or creating chat", task.getException());
            }
        });
    }


    public void goToProfilePage(View view) {
        startActivity(new Intent(InnerChat.this, ChatActivity.class));
        finish();
    }



    // Interface for callback
    public interface OnChatIdGeneratedListener {
        void onChatIdGenerated(String chatId);
    }
    interface OnChatIdResolvedCallback {
        void onChatIdResolved(String chatId);
    }



}