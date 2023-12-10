package com.hao.bilkentconnect.ModelClasses;

import java.util.ArrayList;

public class Meal {
    // Attributes
    private String mealName;
    private String mealDescription;
    private int numberOfUsersRated;
    private int usersRateTotal;
    private double rate;
    private ArrayList<Comment> comments;

    // Constructor
    public Meal() {
        // Initialize the ArrayList
        comments = new ArrayList<>();
    }

    // Methods
    public void addRating(int rating) {
        // TODO: Implement method logic
    }

    public void calculateAverageRating() {
        // TODO: Implement method logic
    }

    public void rateMeal(int rating) {
        // TODO: Implement method logic
    }

    public void updateAverageRating() {
        // TODO: Implement method logic
    }

    @Override
    public String toString() {
        // TODO: Implement the toString method
        return "";
    }

    // Getters and Setters
    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealDescription() {
        return mealDescription;
    }

    public void setMealDescription(String mealDescription) {
        this.mealDescription = mealDescription;
    }

    public int getNumberOfUsersRated() {
        return numberOfUsersRated;
    }

    public void setNumberOfUsersRated(int numberOfUsersRated) {
        this.numberOfUsersRated = numberOfUsersRated;
    }

    public int getUsersRateTotal() {
        return usersRateTotal;
    }

    public void setUsersRateTotal(int usersRateTotal) {
        this.usersRateTotal = usersRateTotal;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
