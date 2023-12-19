package com.hao.bilkentconnect.ModelClasses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Chat {
    private String chatId;
    private List<String> users; // Stores the user IDs
    private ArrayList<ChatMessage> chatMessages;
    private String lastMessage; // Last message text
    private Date lastActivityTime;



    // Constructor
    public Chat(String... users) {
        this.users = Arrays.asList(users);
        this.chatMessages = new ArrayList<>();
    }

    public Chat() {
        this.chatMessages = new ArrayList<>();
    }
    public Chat(String chatId, List<String> users, ArrayList<ChatMessage> chatMessages, String lastMessage, Date lastActivityTime) {
        this.chatId = chatId;
        this.users = users;
        this.chatMessages = chatMessages;
        this.lastMessage = lastMessage;
        this.lastActivityTime = lastActivityTime;
    }


    // Methods
    public void addMessage(ChatMessage message) {
        // TODO: Implement method logic for adding a message to the chat
        chatMessages.add(message);
    }

    // sanırım bu methodu pek kullanmayacağız mesajları silmeyeceğimiz için
    public void removeMessage(ChatMessage message) {
        // TODO: Implement method logic for removing a message from the chat
        chatMessages.remove(message);
    }

    @Override
    public String toString() {
        // TODO: Implement the toString method
        return null;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public ArrayList<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(ArrayList<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public Date getLastActivityTime() {
        return lastActivityTime;
    }

    public void setLastActivityTime(Date lastActivityTime) {
        this.lastActivityTime = lastActivityTime;
    }
}
