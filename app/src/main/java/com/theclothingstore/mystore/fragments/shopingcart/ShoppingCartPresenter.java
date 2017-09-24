package com.theclothingstore.mystore.fragments.shopingcart;

import android.support.annotation.NonNull;

import com.theclothingstore.mystore.data.DataHelper;
import com.theclothingstore.mystore.helper.NullValues;
import com.theclothingstore.mystore.model.CartItem;
import com.theclothingstore.mystore.model.CartResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Anand Soni
 */
class ShoppingCartPresenter {

    private final DataHelper model;
    private ShoppingCartView view;
    private ArrayList<CartResponse> cartResponses;
    private CopyOnWriteArrayList<CartItem> cartItems = new CopyOnWriteArrayList<>();
    private int currentCartId = NullValues.NULL_INT;


    ShoppingCartPresenter(@NonNull DataHelper model,
                          @NonNull ShoppingCartView view) {
        this.cartResponses = model.fetchCartResponse();
        this.model = model;
        this.view = view;
    }

    ArrayList<CartResponse> getCartResponseItems() {
        return cartResponses;
    }

    CopyOnWriteArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    void addCartItems(@NonNull CartItem cartItem) {
        cartItems.add(cartItem);
    }

    void onRemoveCartItem(int cartId) {
        currentCartId = cartId;
        view.onProductRemoveFromCart(true, cartId);
        try {
            model.removeProductFromCart(cartId, responseCallback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void removeProductFromCart(int cartId) {
        for (CartItem cartItem : getCartItems()) {
            if (cartItem.getCartId() == cartId) {
                getCartItems().remove(cartItem);
                break;
            }
        }

        for (CartResponse response : cartResponses) {
            if (response.getCartId() == cartId) {
                getCartResponseItems().remove(response);
                break;
            }
        }

        saveCartResponse();
    }

    private double getTotalPrice() {
        double totalPrice = 0;
        for (CartItem cartItem : getCartItems()) {
            totalPrice += cartItem.getProduct().getProductPrice();
        }

        return totalPrice;
    }

    DataHelper getProductModel() {
        return model;
    }

    void updateCartList() {
        view.updateCartList(getCartItems());
    }

    private Callback<Void> responseCallback = new Callback<Void>() {
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            view.onProductRemoveFromCart(false, NullValues.NULL_INT);
            view.showMessage(true);
            removeProductFromCart(currentCartId);
            currentCartId = NullValues.NULL_INT;
            view.updateTotalCartPrice(getTotalPrice());
        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            view.showMessage(false);
        }
    };

    void setCartItems(CopyOnWriteArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    void showTotalCartPrice() {
        view.updateTotalCartPrice(getTotalPrice());
    }

    private void saveCartResponse() {
        model.saveCartDataLocally(getCartResponseItems());
    }
}
