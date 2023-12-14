package com.hao.bilkentconnect.ModelClasses;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Post {
    public String postId;
    public String sharerId;
    public int likeCount;
    public boolean isAnonymous;
    public String photoUrl;
    public String postDescription;
    public ArrayList<Comment> comments;

    public Date timestamp;


    public Post(String sharerId, String photoUrl, String postDescription, boolean isAnonymous) {
        this.sharerId = sharerId;
        this.photoUrl = photoUrl;
        this.postDescription = postDescription;
        this.likeCount = 0;
        this.isAnonymous = isAnonymous;
        this.comments = new ArrayList<>();
    }

    public Post() {
        this.postId = null;
        this.sharerId = null;
        this.likeCount = 0;
        this.isAnonymous = false;
        this.photoUrl = null;
        this.postDescription = null;
        comments = new ArrayList<>();
    }

    public Post(String postId, String sharerId, String postDescription) {
        this.postId = postId;
        this.sharerId = sharerId;
        this.postDescription = postDescription;
        this.likeCount = 0;
        this.isAnonymous = false;
        this.comments = new ArrayList<>();
    }

    public Post(String sharerId, String postDescription) {
    }

    public Post(String sharerId, int likeCount, String postDescription) {
        this.postId = null;
        this.sharerId = sharerId;
        this.likeCount = likeCount;
        this.isAnonymous = false;
        this.photoUrl = null;
        this.postDescription = postDescription;
        this.comments = new ArrayList<>();
        this.timestamp = null;

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
    public void addComment(Comment comment) {
        if (comments != null) {
            comments.add(comment);
        }
    }


    //burda mevcut postun like sayısını direkt bir artıyoz
    public void likePost() {
        increaseLikeCount();
    }

    public void unlikePost() {
        decreaseLikeCount();
    }
    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }


    public void sharePost(String sharerId, String photoUrl, String postDescription, boolean isAnonymous) {
        Post to_share = new Post( sharerId, photoUrl, postDescription, isAnonymous);
        //ToDo burda oluşturulan yeni post database'eklenecek


    }


    public void increaseLikeCount() {
        likeCount++;
        // butonu devre disi birak

    }

    public void decreaseLikeCount() {
        likeCount--;
        if (likeCount < 0){
            likeCount = 0;
        }
    }

    @Override
    public String toString() {
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

    public ArrayList<Comment> getComments(){
        return comments;
    }
    public String getPostId() {
        return postId;
    }
    public String getPostDescription() {
        return postDescription;
    }
}
