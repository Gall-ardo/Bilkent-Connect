package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.hao.bilkentconnect.ModelClasses.Chat;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfilePageForOthersBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        db = FirebaseStorage.getInstance();
        curr_user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");
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
                finish();  // Finish the activity when the back button is clicked
            }
        });

    }



    public void createChat(){
        Chat chat = new Chat(curr_user,"sSDo3MvvJxfLkSoO1vYw4SYZXsF3");
        HashMap<String, Object> chatData = new HashMap<>();
        chatData.put("user1ID", curr_user);
        chatData.put("user2ID", "sSDo3MvvJxfLkSoO1vYw4SYZXsF3");
        FirebaseUser user = firebaseAuth.getCurrentUser();

    }
    public void addFriendProfilePageForOthers(View view) {
        // add user to friend list


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    
}