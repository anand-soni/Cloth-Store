package com.theclothingstore.mystore.model;

import com.google.gson.annotations.SerializedName;

/**
 * This is a plain old java object or POJO class for the product
 *
 * @author Anand Soni
 */
public class Product {

    @SerializedName("productId")
    private int productId;
    @SerializedName("name")
    private String productName;
    @SerializedName("category")
    private String productCategory;
    @SerializedName("price")
    private double productPrice;
    @SerializedName("oldPrice")
    private Double productOldPrice;
    @SerializedName("stock")
    private int productStock;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public Double getProductOldPrice() {
        return productOldPrice;
    }

    public void setProductOldPrice(Double productOldPrice) {
        this.productOldPrice = productOldPrice;
    }

    public int getProductStock() {
        return productStock;
    }

    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }
}
