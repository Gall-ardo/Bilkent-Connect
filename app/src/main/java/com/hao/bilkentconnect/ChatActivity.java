package com.hao.bilkentconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hao.bilkentconnect.Adapter.ChatAdapter;
import com.hao.bilkentconnect.Adapter.CommentAdapter;
import com.hao.bilkentconnect.ModelClasses.Chat;
import com.hao.bilkentconnect.ModelClasses.User;
import com.hao.bilkentconnect.databinding.ActivityChangePasswordPageBinding;
import com.hao.bilkentconnect.databinding.ActivityChatBinding;

import java.util.ArrayList;

public class ChatActivity extends AppCompatActivity {

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
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid(); //kullanıcı id'si için alıyorsun


        loadChatsFromFirebase(); //aşağıdaki işleri yüklüyor


        binding.recyclerChatView.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(chatArrayList);
        binding.recyclerChatView.setAdapter(chatAdapter);

    }




    private void loadChatsFromFirebase(){

        db.collection("Users").get().addOnSuccessListener(queryDocumentSnapshots -> {

            chatArrayList.clear();
            for (DocumentSnapshot snapshot: queryDocumentSnapshots.getDocuments()){
                User user = snapshot.toObject(User.class);
                if (user != null && user.getId().equals(currentUserId)){
                    for (Chat c: user.getChats()){
                        chatArrayList.add(c);
                    }
                }
            }
            chatAdapter.notifyDataSetChanged();
        }).addOnFailureListener(e -> {

            Toast.makeText(ChatActivity.this, "Error loading posts: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("Firestore Error at chatpage", e.getMessage());
        });

    }






    public void goToMainPageChatActivity(View view){
        Intent intent = new Intent(ChatActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}