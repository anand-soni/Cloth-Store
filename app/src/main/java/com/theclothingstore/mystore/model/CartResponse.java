package com.theclothingstore.mystore.model;

/**
 * This is a simple POJO class for cart response
 *
 * @author Anand Soni
 */

public class CartResponse {
    private int cartId;
    private int productId;

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
