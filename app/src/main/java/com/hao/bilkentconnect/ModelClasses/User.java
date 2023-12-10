package com.hao.bilkentconnect.ModelClasses;

import java.util.ArrayList;

public class User {
    // Attributes
    private int id;
    private String email;
    private String username;
    private String password;
    private String profilePhoto;
    private ArrayList<Post> savedPosts;
    private ArrayList<Post> sharedPosts;
    private ArrayList<User> friends;
    private ArrayList<Product> products;
    private ArrayList<Chat> chats;
    private String biography;

    // Constructor
    public User() {
        // Initialize the ArrayLists
        savedPosts = new ArrayList<>();
        sharedPosts = new ArrayList<>();
        friends = new ArrayList<>();
        products = new ArrayList<>();
        chats = new ArrayList<>();
        // TODO: Add any additional initialization here
    }

    // Getters and Setters
    public int getId() {
        // TODO: Implement method logic
        return id;
    }

    public String getEmail() {
        // TODO: Implement method logic
        return email;
    }

    public String getUsername() {
        // TODO: Implement method logic
        return username;
    }

    public void setUsername(String username) {
        // TODO: Implement method logic
        this.username = username;
    }

    public String getPassword() {
        // TODO: Implement method logic
        return password;
    }

    public void setPassword(String password) {
        // TODO: Implement method logic
        this.password = password;
    }

    public String getProfilePhoto() {
        // TODO: Implement method logic
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        // TODO: Implement method logic
        this.profilePhoto = profilePhoto;
    }

    public ArrayList<Post> getSavedPosts() {
        // TODO: Implement method logic
        return savedPosts;
    }

    public void addSavedPost(Post post) {
        // TODO: Implement method logic
        savedPosts.add(post);
    }

    public void removeSavedPost(Post post) {
        // TODO: Implement method logic
        savedPosts.remove(post);
    }

    public String getBio() {
        // TODO: Implement method logic
        return biography;
    }

    public void setBio(String biography) {
        // TODO: Implement method logic
        this.biography = biography;
    }

    public ArrayList<Post> getSharedPosts() {
        // TODO: Implement method logic
        return sharedPosts;
    }

    public void sharePost(Post post) {
        // TODO: Implement method logic
        sharedPosts.add(post);
    }

    public ArrayList<User> getFriends() {
        // TODO: Implement method logic
        return friends;
    }

    public void addFriend(User friend) {
        // TODO: Implement method logic
        friends.add(friend);
    }

    public void removeFriend(User friend) {
        // TODO: Implement method logic
        friends.remove(friend);
    }

    public ArrayList<Product> getProducts() {
        // TODO: Implement method logic
        return products;
    }

    public void sellProduct(Product product) {
        // TODO: Implement method logic
        products.add(product);
    }

    public void removeProduct(Product product) {
        // TODO: Implement method logic
        products.remove(product);
    }

    @Override
    public String toString() {
        // TODO: Implement the toString method
        return "";
    }
}
