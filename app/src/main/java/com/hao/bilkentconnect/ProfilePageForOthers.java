package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.hao.bilkentconnect.ModelClasses.Chat;
import com.hao.bilkentconnect.databinding.ActivityProfilePageForOthersBinding;

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


    }



    public void createChat(){



        Chat chat = new Chat(curr_user,"sSDo3MvvJxfLkSoO1vYw4SYZXsF3");

        HashMap<String, Object> chatData = new HashMap<>();

        chatData.put("user1ID", curr_user);
        chatData.put("user2ID", "sSDo3MvvJxfLkSoO1vYw4SYZXsF3");


        FirebaseUser user = firebaseAuth.getCurrentUser();






    }



    
}