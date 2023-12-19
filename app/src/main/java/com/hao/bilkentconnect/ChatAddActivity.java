package com.hao.bilkentconnect;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.hao.bilkentconnect.Adapter.ChatAdapter;
import com.hao.bilkentconnect.Adapter.UserAdapter;
import com.hao.bilkentconnect.ModelClasses.Chat;
import com.hao.bilkentconnect.ModelClasses.User;
import com.hao.bilkentconnect.databinding.ActivityChatAddBinding;
import com.hao.bilkentconnect.databinding.ActivityChatBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class ChatAddActivity extends AppCompatActivity implements OnUserClickListener {

    private ActivityChatAddBinding binding;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    String currentUserId;
    private ArrayList<User> friendList;
    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        friendList = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUserId = firebaseAuth.getCurrentUser().getUid();

        loadFriendsFromFirebase();

        userAdapter = new UserAdapter(friendList, this);
        binding.recyclerAddChatView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerAddChatView.setAdapter(userAdapter);
    }

    private void loadFriendsFromFirebase() {
        db.collection("Users").document(currentUserId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        User currentUser = documentSnapshot.toObject(User.class);
                        if (currentUser != null && currentUser.getFriendIds() != null) {
                            fetchFriends(currentUser.getFriendIds());
                        }
                    }
                })
                .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Error loading user: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void fetchFriends(List<String> friendIds) {
        friendList.clear();
        for (String friendId : friendIds) {
            db.collection("Users").document(friendId)
                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                Log.e("Firestore Error", e.getMessage());
                                return;
                            }

                            if (documentSnapshot != null && documentSnapshot.exists()) {
                                User friend = documentSnapshot.toObject(User.class);
                                if (friend != null) {
                                    // Ensure no duplicate entries
                                    if (friendList.stream().noneMatch(f -> f.getUserId().equals(friend.getUserId()))) {
                                        friendList.add(friend);
                                        userAdapter.notifyDataSetChanged();
                                    }
                                }
                            }
                        }
                    });
        }
    }



    @Override
    public void onUserClick(User user) {
        Intent intent = new Intent(ChatAddActivity.this, InnerChat.class);
        intent.putExtra("otherUserId", user.getUserId());
        startActivity(intent);
    }
    public void goToChatPage(View view) {
        Intent intent = new Intent(ChatAddActivity.this, ChatActivity.class);
        startActivity(intent);
        finish();
    }
}
