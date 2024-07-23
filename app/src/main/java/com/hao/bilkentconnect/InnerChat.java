package com.hao.bilkentconnect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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

import okhttp3.internal.Internal;
import okhttp3.internal.Util;

public class InnerChat extends AppCompatActivity {
    private ActivityInnerChatBinding binding;
    private FirebaseFirestore db;

    private FirebaseAuth myAuth;
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
        myAuth = FirebaseAuth.getInstance();

        currentUserId = myAuth.getCurrentUser().getUid();

        Intent intent = getIntent();
        otherUserId = intent.getStringExtra("otherId");



        db.collection("Chats")
                .whereIn("firstUserId",Arrays.asList(currentUserId, otherUserId))
                .whereIn("secondUserId", Arrays.asList(currentUserId, otherUserId)).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                        for (DocumentSnapshot doc: queryDocumentSnapshots){

                            //TODO burda bir şeyler yapmamız gerekiyor sanırım ama anlamadın
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(InnerChat.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        loadOtherUserDetails(otherUserId);



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


    private void startMessaging(){

        db.collection("Chats").whereArrayContains("users", Arrays.asList(currentUserId, otherUserId))
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Toast.makeText(InnerChat.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                        }

                        if (value != null){
                            updateUI(value);
                        }
                    }
                });

    }


    private void updateUI(QuerySnapshot query){


        for (DocumentSnapshot doc: query){

            Chat currChat = doc.toObject(Chat.class);

            ArrayList<ChatMessage> messages = currChat.getChatMessages();

            for (ChatMessage m: messages){
                if (currentUserId.equals(m.getSenderId())){
                    m.setMine(true);
                }
            }

            settingRecyclerView(messages);
            break;

        }

    }

    private void settingRecyclerView(ArrayList<ChatMessage> messages){

        binding.recyclerChatMessageView.setLayoutManager(new LinearLayoutManager(InnerChat.this));
        ChatMessageAdapter chatMessageAdapter = new ChatMessageAdapter(messages);

        binding.recyclerChatMessageView.scrollToPosition(chatMessageAdapter.getItemCount() - 1);
        binding.recyclerChatMessageView.setAdapter(chatMessageAdapter);

        chatMessageAdapter.notifyDataSetChanged();

    }


    public void backButtonClicked(View view){

        db.collection("Chats")
                .whereArrayContains("users", Arrays.asList(currentUserId, otherUserId))
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {



                        for (DocumentSnapshot doc: queryDocumentSnapshots){
                            String docId = doc.getId();

                            Chat c = doc.toObject(Chat.class);

                            for (ChatMessage m: c.getChatMessages()){
                                if (m.getSenderId().equals(otherUserId) && !m.isRead()){
                                    m.setRead(true);
                                }
                            }

                            db.collection("Chats").document(docId).update("chatMessages", c.getChatMessages());

                            Intent intent = new Intent(InnerChat.this,ChatActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(InnerChat.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                });

    }




























}