package com.theclothingstore.mystore.fragments.shopingcart;

import com.theclothingstore.mystore.model.CartItem;

import java.util.List;

/**
 * @author Anand Soni
 */

interface ShoppingCartView {
    void updateCartList(List<CartItem> cartItems);

    void onProductRemoveFromCart(boolean running, int cartId);

    void showMessage(boolean success);

    void updateTotalCartPrice(double totalPrice);
}
