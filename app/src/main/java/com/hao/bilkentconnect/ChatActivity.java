package com.hao.bilkentconnect;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.hao.bilkentconnect.Adapter.ChatAdapter;
import com.hao.bilkentconnect.Adapter.CommentAdapter;
import com.hao.bilkentconnect.ModelClasses.Chat;
import com.hao.bilkentconnect.ModelClasses.ChatMessage;
import com.hao.bilkentconnect.ModelClasses.User;
import com.hao.bilkentconnect.databinding.ActivityChatBinding;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity{


    private ArrayList<Chat> chatArrayList;
    private ActivityChatBinding binding;

    public FirebaseFirestore db;
    public FirebaseAuth firebaseAuth;
    private String currentUserId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);


        chatArrayList = new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        getChats();





    }

    private void getChats(){

        db.collection("Chats")
                .whereArrayContains("users", currentUserId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){

                            System.out.println("Error burada");
                            Toast.makeText(ChatActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }

                        if (value != null){

                            chatArrayList.clear();
                            for (QueryDocumentSnapshot q: value){
                                Chat c = q.toObject(Chat.class);

                                int counter = 0;
                                for (int i = 0; i < c.getChatMessages().size(); i++){
                                    ChatMessage cm = c.getChatMessages().get(i);
                                    if (!cm.getSenderId().equals(currentUserId) && !cm.isRead()){
                                        counter++;
                                    }
                                }

                                c.setUnreadMessageCount(counter);

                                chatArrayList.add(c);
                            }


                            settingRecyclerView();

                        }

                    }
                });
    }


    private void settingRecyclerView(){


        System.out.println("sistem buraya girdi chat arrayi karşıdaki gibidir: ");
        System.out.println(chatArrayList);

        binding.recyclerChatView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
        ChatAdapter chatAdapter = new ChatAdapter(chatArrayList);
        binding.recyclerChatView.setAdapter(chatAdapter);
        chatAdapter.notifyDataSetChanged();



    }


    public void goToMainPageChatActivity(View view){
        Intent intent = new Intent(ChatActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

   

    public void goToAddChatActivity(View view){
        Intent intent = new Intent(ChatActivity.this, ChatAddActivity.class);
        startActivity(intent);
        finish();
    }
}
