package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.hao.bilkentconnect.ModelClasses.Chat;
import com.hao.bilkentconnect.ModelClasses.User;
import com.hao.bilkentconnect.databinding.ActivityProfilePageForOthersBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class ProfilePageForOthers extends AppCompatActivity {

    private ActivityProfilePageForOthersBinding binding;
    FirebaseStorage db;
    FirebaseAuth firebaseAuth;
    String curr_user;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfilePageForOthersBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        db = FirebaseStorage.getInstance();
        curr_user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Intent intent = getIntent();
        userId = intent.getStringExtra("userId");
        String username = intent.getStringExtra("username");
        String profilePhoto = intent.getStringExtra("profilePhoto");
        String biography = intent.getStringExtra("biography");

        // Set the data to the views
        binding.usernameText.setText(username);
        binding.biographyText.setText(biography);
        if (profilePhoto != null && !profilePhoto.isEmpty()) {
            Picasso.get().load(profilePhoto).into(binding.profileImage);
        } else {
            binding.profileImage.setImageResource(R.drawable.profile_icon); // Replace with your default image resource
        }
        ImageView backButtonImage = findViewById(binding.backButtonImage.getId());
        backButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void addFriendProfilePageForOthers(View view) {
        if (userId == null) {
            Toast.makeText(this, "Profile user ID is not available.", Toast.LENGTH_SHORT).show();
            return;
        }
        String currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Users").document(currentUserId)
                .update("friendIds", FieldValue.arrayUnion(userId))
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Friend added successfully!", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Failed to add friend: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void goBacktoPostView(View view) {
        finish();
    }
    public void goToChatPage(View view) {
        if (userId == null) {
            Toast.makeText(this, "User ID is not available.", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users").document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        User user = documentSnapshot.toObject(User.class);
                        if (user != null) {
                            String email = user.getEmail();
                            Toast.makeText(this, "Email: " + email, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(this, "User not found.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error fetching user details: " + e.getMessage(), Toast.LENGTH_LONG).show());
    }




}