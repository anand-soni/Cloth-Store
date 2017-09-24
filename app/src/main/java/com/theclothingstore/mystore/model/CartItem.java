package com.theclothingstore.mystore.model;

/**
 * @author Anand Soni
 */

public class CartItem {
    private Product product;
    private int cartId;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }
}

