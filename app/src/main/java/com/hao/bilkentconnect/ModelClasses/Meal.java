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
    public Meal(String mealName, String mealDescription) {
        this.mealName = mealName;
        this.mealDescription = mealDescription;
        numberOfUsersRated = 0;
        usersRateTotal = 0;
        rate = 10; // maks puan buraya gelecek
        comments = new ArrayList<>();
    }

    // Methods
    public void addRating(int rating) {
        usersRateTotal += rating;
    }

    private double calculateAverageRating() {
        return usersRateTotal/numberOfUsersRated;
    }

    public void rateMeal(int rating) {
        addRating(rating);
        numberOfUsersRated++;
    }

    public void updateAverageRating() {

        rate = calculateAverageRating();
    }

    @Override
    public String toString() {

        return "Meal " + mealName + "desc " + mealDescription + "TotalRate " + usersRateTotal + "number of user " +numberOfUsersRated + "rate " + rate;
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

    public void addComment (Comment meal_comment){
        comments.add(meal_comment);
    }



    /*buraya aradığın comment Id'sini yaz en sonunda sana commentin kendini döndürecek
    eğer aradığın comment yoksa null döndürür
     */
    public Comment getSpesificComment (int commentId){

        for (int i = 0; i <= comments.size(); i++){
            if (comments.get(i).getCommentId() == commentId){
                return comments.get(i);
            }
        }

        return null;
    }
}
