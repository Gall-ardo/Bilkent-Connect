package com.hao.bilkentconnect.ModelClasses;

import java.util.ArrayList;

public class User {
    // Attributes
    private String userId; // to reach unique user in database
    private String email;
    private String username;
    private String bio;
    private String profilePhoto;
    private ArrayList<Post> savedPosts;
    private ArrayList<String> likedPosts;
    private ArrayList<Post> sharedPosts;
    private ArrayList<String> friendIds;
    private ArrayList<Product> products;
    private ArrayList<Chat> chats;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSavedPosts(ArrayList<Post> savedPosts) {
        this.savedPosts = savedPosts;
    }

    public void setSharedPosts(ArrayList<Post> sharedPosts) {
        this.sharedPosts = sharedPosts;
    }

    public ArrayList<String> getFriendIds() {
        return friendIds;
    }

    public void setFriendIds(ArrayList<String> friendIds) {
        this.friendIds = friendIds;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void setChats(ArrayList<Chat> chats) {
        this.chats = chats;
    }

    // Constructor
    public User() {
        userId = "sample user id";
        email = "sample email";
        username = "sample username";
        bio = "sample bio";
        //profilePhoto = "sample profile photo url ";
        // Initialize the ArrayLists
        savedPosts = new ArrayList<>();
        sharedPosts = new ArrayList<>();
        friendIds = new ArrayList<>();
        products = new ArrayList<>();
        chats = new ArrayList<>();
        likedPosts = new ArrayList<>();
    }

    public User(String userId, String email, String username) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.savedPosts = new ArrayList<>();
        this.sharedPosts = new ArrayList<>();
        this.friendIds = new ArrayList<>();
        this.products = new ArrayList<>();
        this.chats = new ArrayList<>();
        this.likedPosts = new ArrayList<>();
        this.profilePhoto = "https://firebasestorage.googleapis.com/v0/b/bilkentconnect-344eb.appspot.com/o/default%20pp.png?alt=media&token=c8bba3a4-34aa-4122-b909-cffc7026128a";
        this.bio = "You can add bio in edit profile page!";
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

    public ArrayList<String> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(ArrayList<String> likedPosts) {
        this.likedPosts = likedPosts;
    }

    public ArrayList<Chat> getChats(){
        return chats;
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


    public void addChat(Chat c){
        chats.add(c);
    }


    public ArrayList<String> getFriends() {
        // TODO: Implement method logic
        return friendIds;
    }

    public void addFriend(String friendId) {
        // TODO: Implement method logic
        friendIds.add(friendId);
    }

    public void removeFriend(String friendId) {
        // TODO: Implement method logic
        friendIds.remove(friendId);
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
