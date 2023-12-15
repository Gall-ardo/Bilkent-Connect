package com.hao.bilkentconnect.ModelClasses;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Product implements Serializable {
    // Attributes
    private String productId;
    private String productName;
    private String image;
    private String description;
    private String seller; // Assuming 'seller' is represented by an integer ID
    private String price;
    public Date timestamp;


    // Constructor
    public Product(String productName, String image, String description, String seller, String price) {
        this.productName = productName;
        this.image = image;
        this.description = description;
        this.seller = seller;
        this.price = price;
    }
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("productId", productId);
        result.put("productName", productName);
        result.put("image", image);
        result.put("description", description);
        result.put("seller", seller);
        result.put("price", price);
        result.put("timestamp", timestamp);
        return result;
    }

    public String getProductId() {
        return productId;
    }
// Methods

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

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
