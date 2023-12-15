package com.hao.bilkentconnect;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.hao.bilkentconnect.ModelClasses.Comment;
import com.hao.bilkentconnect.ModelClasses.User;
import com.hao.bilkentconnect.databinding.ActivityProfilePageBinding;
import com.hao.bilkentconnect.ui.login.LoginActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfilePage extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    ActivityProfilePageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfilePageBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if (currentUser != null) {
            String userId = currentUser.getUid();
            db.collection("Users").document(userId).get().addOnSuccessListener(documentSnapshot -> {

                if (documentSnapshot.exists()) {
                    System.out.println("Checkpoint 1");
                    User user = documentSnapshot.toObject(User.class);
                    System.out.println("Checkpoint 2");
                    if (user != null) {
                        System.out.println("Checkpoint 3");
                        binding.userMailText.setText(user.getEmail());
                        binding.userNameText.setText(user.getUsername());
                        binding.userInfo.setText(user.getBio());
                        System.out.println("Checkpoint 4");

                        if (user.getProfilePhoto() != null) {
                            System.out.println("Checkpoint 5");
                            Picasso.get().load(user.getProfilePhoto()).into(binding.UserImage);
                            System.out.println("Checkpoint 6");
                        }
                    }
                    System.out.println("Checkpoint 7");
                }
            }).addOnFailureListener(e -> {
                Toast.makeText(ProfilePage.this, "Error loading user info", Toast.LENGTH_SHORT).show();
            });
        }
    }


    public void goToMainPage(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    public void goToChatPage(View view) {
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }
    public void goToSharePostActivity(View view){
        Intent intent = new Intent(this, SharePostsActivity.class);
        startActivity(intent);
    }

    public void goToEditProfilePage(View view){
        Intent intent = new Intent(this, EditProfilePage.class);
        startActivity(intent);
        finish();
    }

    public void goToYourPostsPage(View view){
        Intent intent = new Intent(this, OwnPostPage.class);
        startActivity(intent);
        finish();

    }

    public void goToSavedPostsPage(View view){
        Intent intent = new Intent(this, SavedPostPages.class);
        startActivity(intent);
        finish();

    }

    public void goToSecurityPage(View view){
        Intent intent = new Intent(this, ChangePasswordPage.class);
        startActivity(intent);
        finish();

    }

    public void goToLogOutPage(View view){
        firebaseAuth.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

}