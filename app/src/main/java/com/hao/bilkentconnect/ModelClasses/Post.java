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


    //burda mevcut postun like sayısını direkt bir artıyoz
    public void likePost() {
        increaseLikeCount();
    }

    public void unlikePost() {
        decreaseLikeCount();
    }
    public Date getTimestamp() { return timestamp; }
    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }


    //comments listesinin sonuna direkt parametredeki commenti ekliyoz ekliyoruz
    public void addComment(Comment comment) {
        comments.add(comment);
        // TODO: Additional implementation if needed
    }



    //burda comment listesi içinde ilerliyoz, eğer ararığımızkiyle ID'ler eşitse direkt o indexi siliyoz
    public void removeComment(Comment comment) {

        for (int i = 0; i <= comments.size(); i++){
            if (comments.get(i).getCommentId() == comment.getCommentId()){
                comments.remove(i);
            }
        }
    }

    public void sharePost(String sharerId, String photoUrl, String postDescription, boolean isAnonymous) {
        Post to_share = new Post( sharerId, photoUrl, postDescription, isAnonymous);
        //ToDo burda oluşturulan yeni post database'eklenecek


    }

    public void savePost() {
        // TODO: Implement method logic
    }





    public void increaseLikeCount() {
        likeCount++;
        // TODO: Additional implementation if needed
    }

    public void decreaseLikeCount() {
        likeCount--;
        if (likeCount < 0){
            likeCount = 0;
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


    public ArrayList<Comment> getComments(){
        return comments;
    }
}
