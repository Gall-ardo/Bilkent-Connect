package com.hao.bilkentconnect.ModelClasses;

import java.util.Date;

public class MealComment {
    private String commentId;
    private String commentText;
    private String commentSenderId;
    private boolean isAnonymous;
    private String mealDate;
    private Date timestamp;
    private String mealType; // "lunch" or "dinner"


    public MealComment() {
    }
    public MealComment(String commentId, String commentSenderId, String commentText,
                       boolean isAnonymous, String mealDate, String mealType, Date timestamp) {
        this.commentId = commentId;
        this.commentSenderId = commentSenderId;
        this.commentText = commentText;
        this.isAnonymous = isAnonymous;
        this.mealDate = mealDate;
        this.mealType = mealType;
        this.timestamp = timestamp;
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

    public String getCommentSenderId() {
        return commentSenderId;
    }

    public void setCommentSenderId(String commentSenderId) {
        this.commentSenderId = commentSenderId;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setAnonymous(boolean anonymous) {
        isAnonymous = anonymous;
    }

    public String getMealDate() {
        return mealDate;
    }

    public void setMealDate(String mealDate) {
        this.mealDate = mealDate;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }
}
