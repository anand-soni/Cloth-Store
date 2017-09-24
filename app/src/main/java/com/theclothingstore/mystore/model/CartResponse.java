package com.theclothingstore.mystore.model;

import com.google.gson.annotations.SerializedName;

/**
 * This is a simple POJO class for cart response
 *
 * @author Anand Soni
 */

public class CartResponse {

    @SerializedName("cartId")
    private int cartId;
    @SerializedName("productId")
    private int productId;

    public int getCartId() {
        return cartId;
    }

    public int getProductId() {
        return productId;
    }
}
