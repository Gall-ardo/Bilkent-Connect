package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hao.bilkentconnect.Adapter.ChatMessageAdapter;
import com.hao.bilkentconnect.Adapter.CommentAdapter;
import com.hao.bilkentconnect.ModelClasses.ChatMessage;

import com.hao.bilkentconnect.ModelClasses.User;
import com.hao.bilkentconnect.databinding.ActivityInnerChatBinding;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.Date;

public class InnerChat extends AppCompatActivity {
    private ChatMessageAdapter chatMessageAdapter;
    private ArrayList<ChatMessage> chatMessageArrayList;
    private ActivityInnerChatBinding binding;
    String otherUserId;
    String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

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

        if (otherUserId == null) {
            Toast.makeText(this, "No user ID received", Toast.LENGTH_LONG).show();
            return;
        }

        ChatMessage message = new ChatMessage( currentUserId,otherUserId, messageText, new Timestamp(new Date()));
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("ChatMessages").add(message)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();
                    // Update your UI here if necessary
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error sending message: " + e.getMessage(), Toast.LENGTH_SHORT).show());

        binding.directMessageText.setText("");

    }

}