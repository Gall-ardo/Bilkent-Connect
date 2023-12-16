package com.hao.bilkentconnect.ModelClasses;

import com.google.firebase.Timestamp;

import java.util.Date;

public class ChatMessage {
    // Attributes
    private String senderId; // Assuming 'sender' is represented by an integer ID
    private String receiverId; // Assuming 'receiver' is represented by an integer ID
    private String text;

    private Date timestap; // Using Timestamp for the timeStap

    // Constructor
    public ChatMessage(String sender, String receiver, String text, Timestamp timestamp) {
        this.senderId = sender;
        this.receiverId = receiver;
        this.text = text;
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
}
