package com.theclothingstore.mystore.fragments.shopingcart;

import android.content.SharedPreferences;
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

    /**
     * Constructor for the presenter
     *
     * @param model, DataHelper class instance
     * @param view, interface for communicating with the view
     */
    ShoppingCartPresenter(@NonNull DataHelper model,
                          @NonNull ShoppingCartView view) {
        this.cartResponses = model.fetchCartResponse();
        this.model = model;
        this.view = view;
    }

    /**
     * This method return the cart response items
     *
     * @return {@link ArrayList<CartResponse>}, list of  cart responses
     */
    ArrayList<CartResponse> getCartResponseItems() {
        return cartResponses;
    }

    /**
     * This method return the cart items
     *
     * @return {@link CopyOnWriteArrayList<CartItem>} list of cart items
     */
    CopyOnWriteArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    /**
     * This method add the cart item to the list
     *
     * @param cartItem, {@link CartItem} instance which need to be added
     */
    void addCartItems(@NonNull CartItem cartItem) {
        cartItems.add(cartItem);
    }

    /**
     * This method communicates with the {@link DataHelper} and remove the
     * product from the cart
     *
     * @param cartId, cart id of the product which needs to be removed
     */
    void onRemoveCartItem(int cartId) {
        currentCartId = cartId;
        view.onProductRemoveFromCart(true, cartId);
        try {
            model.removeProductFromCart(cartId, responseCallback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  After successful removal of the cart item, this method performs the local
     *  cart items and local cart responses update and also save the cart responses
     *  in the {@link SharedPreferences} for future use
     *
     * @param cartId, cart id which need to be removed
     */
    void removeProductFromCart(int cartId) {
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
        //save the cart response locally
        saveCartResponse();
    }

    /**
     * This method get the total price for the cart items
     *
     * @return double, total price of the cart items
     */
    double getTotalPrice() {
        double totalPrice = 0;
        for (CartItem cartItem : getCartItems()) {
            totalPrice += cartItem.getProduct().getProductPrice();
        }

        return totalPrice;
    }

    /**
     * This is a helper method for get the getting the {@link DataHelper}
     *
     * @return {@link DataHelper} instance
     */
    DataHelper getDataHelper() {
        return model;
    }

    /**
     * This method update the cart items on UI
     */
    void updateCartList() {
        view.updateCartList(getCartItems());
    }

    /**
     * Callback response for product removal from the cart
     */
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
            view.onProductRemoveFromCart(false, NullValues.NULL_INT);
            view.showMessage(false);
        }
    };

    /**
     * Setter for the cart items
     *
     * @param cartItems, list of cart items
     */
    void setCartItems(CopyOnWriteArrayList<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    /**
     * This method update the total price on UI
     */
    void showTotalCartPrice() {
        view.updateTotalCartPrice(getTotalPrice());
    }

    /**
     *  This method save the cart response locally for the future use
     */
    void saveCartResponse() {
        model.saveCartDataLocally(getCartResponseItems());
    }

    // for test purpose
    void setCartResponses(ArrayList<CartResponse> cartResponses) {
        this.cartResponses = cartResponses;
    }
}
