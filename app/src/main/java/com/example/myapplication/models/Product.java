package com.example.myapplication.models;

import java.util.List;

public class Product {
    private String categoryName, desc, price, productId, productName, uId;


    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    private String quantity;
    private List<String>listImageUrl;

    public Product(){

    }
    public Product(String categoryName, String desc, String price, String productId, String productName, String quantity, String uId, List<String> listImageUrl) {
        this.categoryName = categoryName;
        this.desc = desc;
        this.price = price;
        this.productId = productId;
        this.productName = productName;
        this.uId = uId;
        this.listImageUrl = listImageUrl;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public List<String> getListImageUrl() {
        return listImageUrl;
    }

    public void setListImageUrl(List<String> listImageUrl) {
        this.listImageUrl = listImageUrl;
    }
}
