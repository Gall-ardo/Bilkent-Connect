package com.hao.bilkentconnect.ModelClasses;

import java.util.ArrayList;

public class Post {
    // Attributes
    private int sharer;
    private int likeCount;
    private int id;
    private boolean isAnonymous;
    private String photo;
    private String postDescription;
    private ArrayList<Comment> comments;

    // Constructor
    public Post() {
        // Initialize the ArrayList
        comments = new ArrayList<>();
    }

    // Methods
    public void likePost() {
        // TODO: Implement method logic
    }

    public void unlikePost() {
        // TODO: Implement method logic
    }

    public void commentOnPost(Comment comment) {
        // TODO: Implement method logic
    }

    public void deleteComment(Comment comment) {
        // TODO: Implement method logic
    }

    public void sharePost() {
        // TODO: Implement method logic
    }

    public void savePost() {
        // TODO: Implement method logic
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        // TODO: Additional implementation if needed
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        // TODO: Additional implementation if needed
    }

    public void increaseLikeCount() {
        likeCount++;
        // TODO: Additional implementation if needed
    }

    public void decreaseLikeCount() {
        if (likeCount > 0) {
            likeCount--;
            // TODO: Additional implementation if needed
        }
    }

    @Override
    public String toString() {
        // TODO: Implement the toString method
        return "";
    }

    // Getters and Setters
    // TODO: Add getters and setters for all attributes
}
