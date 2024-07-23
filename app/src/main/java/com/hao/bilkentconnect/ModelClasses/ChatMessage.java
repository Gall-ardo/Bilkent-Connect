package com.hao.bilkentconnect.ModelClasses;

import com.google.firebase.Timestamp;

import org.checkerframework.checker.units.qual.C;

import java.util.Date;

public class ChatMessage {
    // Attributes
    private String senderId; // Assuming 'sender' is represented by an integer ID
    private String receiverId; // Assuming 'receiver' is represented by an integer ID
    private String text;
    private String chatId;
    private Date timestap; // Using Timestamp for the timeStap

    private boolean isMine;

    private boolean isRead;

    // Constructor
    public ChatMessage(String sender, String receiver, String text, String chatId,  Timestamp timestamp) {
        this.senderId = sender;
        this.receiverId = receiver;
        this.text = text;
        this.chatId = chatId;
        this.timestap = timestamp.toDate();
        isMine = false;
    }
    public ChatMessage(String sender, String receiver, String text,Timestamp timestamp) {
        this.senderId = sender;
        this.receiverId = receiver;
        this.text = text;
        this.timestap = timestamp.toDate();
        isMine = false;
    }




    // Methods
    public void send() {
        // TODO: Implement method logic for sending a message
    }

    public void delete() {
        // TODO: Implement method logic for deleting a message
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "sender=" + senderId +
                ", receiver=" + receiverId +
                ", text='" + text + '\'' +
                ", time=" + timestap +
                '}';
    }

    public String getSenderId() {
        return senderId;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimestap() {
        return timestap;
    }

    public void setTimestap(Date timestap) {
        this.timestap = timestap;
    }


    public boolean isMine() {
        return isMine;
    }

    public void setMine(boolean mine) {
        isMine = mine;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
