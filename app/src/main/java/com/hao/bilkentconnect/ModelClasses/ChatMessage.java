package com.hao.bilkentconnect.ModelClasses;

import java.sql.Timestamp;

public class ChatMessage {
    // Attributes
    private int sender; // Assuming 'sender' is represented by an integer ID
    private int receiver; // Assuming 'receiver' is represented by an integer ID
    private String text;
    private Timestamp time; // Using Timestamp for the timeStap

    // Constructor
    public ChatMessage(int sender, int receiver, String text, Timestamp time) {
        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.time = time;
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
        // TODO: Implement the toString method
        return "ChatMessage{" +
                "sender=" + sender +
                ", receiver=" + receiver +
                ", text='" + text + '\'' +
                ", time=" + time +
                '}';
    }

    // Getters and Setters
    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
