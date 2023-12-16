package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hao.bilkentconnect.Adapter.ChatMessageAdapter;
import com.hao.bilkentconnect.Adapter.CommentAdapter;
import com.hao.bilkentconnect.ModelClasses.ChatMessage;

import com.hao.bilkentconnect.ModelClasses.User;
import com.hao.bilkentconnect.databinding.ActivityInnerChatBinding;
import com.squareup.picasso.Picasso;


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

        String otherUserId = getIntent().getStringExtra("otherUserId");
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

        chatMessageArrayList = new ArrayList<>();
        binding.recyclerChatMessageView.setLayoutManager(new LinearLayoutManager(this));
        chatMessageAdapter = new ChatMessageAdapter(chatMessageArrayList);
        binding.recyclerChatMessageView.setAdapter(chatMessageAdapter);
    }

}