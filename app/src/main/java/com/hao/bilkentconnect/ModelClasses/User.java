package com.hao.bilkentconnect.ModelClasses;

import java.util.ArrayList;

public class User {
    // Attributes
    private String userId;
    private String email;
    private String username;
    private String profilePhoto;
    private ArrayList<Post> savedPosts;
    private ArrayList<Post> sharedPosts;
    private ArrayList<User> friends;
    private ArrayList<Product> products;
    private ArrayList<Chat> chats;
    private String bio;

    // Constructor
    public User() {
        userId = "id dedigin";
        email = "email dedigin";
        username = "username dedigin";
        bio = "biography dedigin";
        // Initialize the ArrayLists
        savedPosts = new ArrayList<>();
        sharedPosts = new ArrayList<>();
        friends = new ArrayList<>();
        products = new ArrayList<>();
        chats = new ArrayList<>();
        // TODO: Add any additional initialization here
    }
    public User(String userId, String email, String username) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.savedPosts = new ArrayList<>();
        this.sharedPosts = new ArrayList<>();
        this.friends = new ArrayList<>();
        this.products = new ArrayList<>();
        this.chats = new ArrayList<>();
        this.profilePhoto = "";
        this.bio = "";
    }

    // Getters and Setters
    public String getId() {
        // TODO: Implement method logic
        return userId;
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



    public String getProfilePhoto() {
        // TODO: Implement method logic
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        if(profilePhoto == "default_profile_photo_url"){
            profilePhoto = null;
        }
        // TODO: Implement method logic
        this.profilePhoto = profilePhoto;

    }

    public ArrayList<Post> getSavedPosts() {
        // TODO: Implement method logic
        return savedPosts;
    }


    /*
    * return an saved post for special indexes, write baginning and ending index these indexes including
    */
    public ArrayList<Post> getSavedPost(int from, int to){
        ArrayList<Post> savedPosts_spesific = new ArrayList<>();
        for (int i = from; i <= to; i++ ){

            savedPosts_spesific.add(savedPosts.get(i));
        }
        return savedPosts_spesific;
    }

    public void addSavedPost(Post post) {
        // TODO: Implement method logic
        savedPosts.add(post);
    }



    public String getBio() {
        // TODO: Implement method logic
        return bio;
    }

    public void setBio(String biography) {
        // TODO: Implement method logic
        this.bio = biography;
    }

    public ArrayList<Post> getSharedPosts() {
        // TODO: Implement method logic
        return sharedPosts;
    }

    public void removeSavedPost(Post post){
        savedPosts.remove(post);
    }

    public ArrayList<Post> getSharedPost(int from, int to){
        ArrayList<Post> sharedPosts_spesific = new ArrayList<>();
        for (int i = from; i <= to; i++ ){

            sharedPosts_spesific.add(savedPosts.get(i));
        }
        return sharedPosts_spesific;
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
