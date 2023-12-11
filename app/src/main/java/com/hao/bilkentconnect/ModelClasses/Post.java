package com.hao.bilkentconnect.ModelClasses;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Post {

    private String postId;
    private String sharerId; // Using a user ID instead of an email
    private int likeCount;
    private boolean isAnonymous;
    private String photoUrl;
    private String postDescription;
    private ArrayList<Comment> comments;
    private Date timestamp;


    public Post(String sharerId, String photoUrl, String postDescription, boolean isAnonymous) {
        this.sharerId = sharerId;
        this.photoUrl = photoUrl;
        this.postDescription = postDescription;
        this.likeCount = 0;
        this.isAnonymous = isAnonymous;
        this.comments = new ArrayList<>();
    }

    public Post() {
        comments = new ArrayList<>();
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("postId", postId);
        result.put("sharerId", sharerId); // Store the user ID
        result.put("likeCount", likeCount);
        result.put("isAnonymous", isAnonymous);
        result.put("photoUrl", photoUrl);
        result.put("postDescription", postDescription);
        result.put("comments", comments);
        result.put("timestamp", timestamp);
        return result;
    }

    public Post(String postId, String sharerId, int likeCount, boolean isAnonymous, String photoUrl, String postDescription, ArrayList<Comment> comments, Date timestamp) {
        this.postId = postId;
        this.sharerId = sharerId;
        this.likeCount = likeCount;
        this.isAnonymous = isAnonymous;
        this.photoUrl = photoUrl;
        this.postDescription = postDescription;
        this.comments = comments;
        this.timestamp = timestamp;
    }

    // Methods
    public void likePost() {
        // TODO: Implement method logic
    }

    public void unlikePost() {
        // TODO: Implement method logic
    }
    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }

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
        return "Post{" +
                "postId='" + postId + '\'' +
                ", sharerId='" + sharerId + '\'' +
                ", likeCount=" + likeCount +
                ", isAnonymous=" + isAnonymous +
                ", photoUrl='" + photoUrl + '\'' +
                ", postDescription='" + postDescription + '\'' +
                ", comments=" + comments +
                ", timestamp=" + timestamp +
                '}';
    }

    // Getters and Setters
    // TODO: Add getters and setters for all attributes
}
