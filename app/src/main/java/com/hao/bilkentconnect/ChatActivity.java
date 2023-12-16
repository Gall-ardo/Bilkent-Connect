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
import com.google.firebase.firestore.QuerySnapshot;
import com.hao.bilkentconnect.Adapter.ChatAdapter;
import com.hao.bilkentconnect.Adapter.CommentAdapter;
import com.hao.bilkentconnect.ModelClasses.Chat;
import com.hao.bilkentconnect.ModelClasses.User;
import com.hao.bilkentconnect.databinding.ActivityChangePasswordPageBinding;
import com.hao.bilkentconnect.databinding.ActivityChatBinding;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity implements OnChatClickListener{

    private ChatAdapter chatAdapter;

    private ArrayList<Chat> chatArrayList;
    private ActivityChatBinding binding;

    public FirebaseFirestore db;
    public FirebaseAuth firebaseAuth;
    private String currentUserId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);

        chatArrayList = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        getChats();


        binding.recyclerChatView.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(chatArrayList, this);
        binding.recyclerChatView.setAdapter(chatAdapter);

    }


    private void getChats() {

        db.collection("Users").whereEqualTo("id", currentUserId).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Toast.makeText(ChatActivity.this, error.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }

                if (value != null){

                    DocumentSnapshot snapshot = value.getDocuments().get(0);
                    Map<String, Object> data = snapshot.getData();

                    chatArrayList = (ArrayList<Chat>) data.get("chats");


                    chatAdapter.notifyDataSetChanged();


                }
            }
        });


    }

    public void goToMainPageChatActivity(View view){
        Intent intent = new Intent(ChatActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void onChatClick(Chat chat) {
        // Example implementation: Start TextingScreen activity with chat details
        Intent intent = new Intent(this, InnerChat.class);
        intent.putExtra("chatId", chat.getChatId());
        // Add other necessary chat details to the intent
        startActivity(intent);
        finish();
    }

    public void goToAddChatActivity(View view){
        Intent intent = new Intent(ChatActivity.this, ChatAddActivity.class);
        startActivity(intent);
        finish();
    }
}
