package com.hao.bilkentconnect.ModelClasses;

public class Product {
    // Attributes
    private String productName;
    private String image;
    private String description;
    private int seller; // Assuming 'seller' is represented by an integer ID
    private int price;

    // Constructor
    public Product(String productName, String image, String description, int seller, int price) {
        this.productName = productName;
        this.image = image;
        this.description = description;
        this.seller = seller;
        this.price = price;
    }

    // Methods

    //Bence bu fonksiyon burda işlevsiz
    public void contactWithSeller() {
        // TODO: Implement method logic for contacting the seller
    }

    //remove product user class'ta mevcut bu yüzden bunu sildim

    @Override
    public String toString() {
        // TODO: Implement the toString method
        return null;
    }

    // Getters and Setters
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSeller() {
        return seller;
    }

    public void setSeller(int seller) {
        this.seller = seller;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
