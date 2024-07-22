package com.hao.bilkentconnect.ModelClasses;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Chat {


    //TODO may be unnecassary
    private String chatId;

    private String firstUserId;
    private String secondUserId;
    private ArrayList<String> users;

    private ArrayList<ChatMessage> chatMessages;
    private int unreadMessageCount;


    public Chat(String firstUserId, String secondUserId) {
        this.firstUserId = firstUserId;
        this.secondUserId = secondUserId;

        this.users = new ArrayList<>();
        users.add(firstUserId);
        users.add(secondUserId);

        this.chatMessages = new ArrayList<>();

        this.unreadMessageCount = 0;
    }

    public String getFirstUserId() {
        return firstUserId;
    }

    public void setFirstUserId(String firstUserId) {
        this.firstUserId = firstUserId;
    }

    public String getSecondUserId() {
        return secondUserId;
    }

    public void setSecondUserId(String secondUserId) {
        this.secondUserId = secondUserId;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public ArrayList<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public int getUnreadMessageCount() {
        return unreadMessageCount;
    }

    public void setUnreadMessageCount(int unreadMessageCount) {
        this.unreadMessageCount = unreadMessageCount;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public static void createChat(Context con,FirebaseFirestore db, String firstUserId, String secondUserId){

        boolean isExist = false;

        db.collection("Chats")
                .whereIn("firstUserId", Arrays.asList(firstUserId, secondUserId))
                .whereIn("secondUserId", Arrays.asList(firstUserId, secondUserId)).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()){
                            Chat newChat = new Chat(firstUserId, secondUserId);

                            db.collection("Chats").add(newChat).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(con, "Chat is created", Toast.LENGTH_LONG).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(con, "Error adding to chats", Toast.LENGTH_LONG).show();
                                }
                            });
                        }

                        else{
                            Toast.makeText(con, "Chat is already", Toast.LENGTH_LONG).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(con, "Failed to retrieve data /chat_class_static method", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
