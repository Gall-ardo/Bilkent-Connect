package com.hao.bilkentconnect.ModelClasses;

public class Comment {
    // Attributes
    private User commentSender;
    private int commentId;
    private String commentText;
    private boolean isAnonymous;

    // Constructor
    public Comment(User commentSender, int commentId, String commentText, boolean isAnonymous) {
        this.commentSender = commentSender;
        this.commentId = commentId;
        this.commentText = commentText;
        this.isAnonymous = isAnonymous;
    }

    // Getters and Setters
    public User getCommentSender() {
        return commentSender;
    }

    public void setCommentSender(User commentSender) {
        this.commentSender = commentSender;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
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

    // TODO: Add any additional methods
}
