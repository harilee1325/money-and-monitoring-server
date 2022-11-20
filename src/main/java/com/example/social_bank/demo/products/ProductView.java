package com.example.social_bank.demo.products;

public class ProductView {

    private String productName;
    private String productPrice;


    public ProductView() {

    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public ProductView(String productName, String productPrice) {
        this.productName = productName;
        this.productPrice = productPrice;
    }
}
