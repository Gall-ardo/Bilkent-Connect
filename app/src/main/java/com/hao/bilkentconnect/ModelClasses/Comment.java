package com.hao.bilkentconnect.ModelClasses;

import org.checkerframework.checker.units.qual.C;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Comment {
    // Attributes
    private String commentSenderId;
    private String commentId;
    private String commentText;
    private boolean isAnonymous;
    private String postId;

    private Date timestamp;

    // Constructor
    public Comment(String commentSender, String commentId, String commentText, boolean isAnonymous, String postId) {
        this.commentSenderId = commentSender;
        this.commentId = commentId;
        this.commentText = commentText;
        this.isAnonymous = isAnonymous;
        this.postId = postId;
        this.timestamp = new Date();
    }

    public Comment(String userID, String commentText, boolean isAnonymous, String postId) {
        this.commentSenderId = userID;
        this.commentText = commentText;
        this.isAnonymous = isAnonymous;
        this.postId = postId;
        this.timestamp = new Date();

    }

    public String getCommentSenderId() {
        return commentSenderId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Comment(){
        this.commentSenderId = null;
        this.commentText = null;
        this.isAnonymous = true;
        this.postId = null;
    }

    // Getters and Setters
    public String getCommentSender() {
        return commentSenderId;
    }

    public void setCommentSender(String commentSender) {
        this.commentSenderId = commentSender;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

    @Override
    public String toString(){
        return "CommentId"+ getCommentId() +"SenderUser" + getCommentSender() + "CommentText" + getCommentText() + "isAnonymous" + isAnonymous();
    }
    public String getPostId() {
        return postId;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> commentMap = new HashMap<>();
        commentMap.put("userId", commentSenderId);
        commentMap.put("commentText", commentText);
        commentMap.put("isAnonymous", isAnonymous);
        commentMap.put("commentId", commentId);
        commentMap.put("postId", postId);
        return commentMap;
    }
}
