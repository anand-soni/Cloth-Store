package com.theclothingstore.mystore.fragments.productcatalogue;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.theclothingstore.mystore.data.DataHelper;
import com.theclothingstore.mystore.helper.NullValues;
import com.theclothingstore.mystore.helper.ResponseCode;
import com.theclothingstore.mystore.model.CartResponse;
import com.theclothingstore.mystore.model.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author Anand Soni
 */

public class ProductCataloguePresenter {

    private ProductCatalogueView view;
    private DataHelper model;
    private List<Product> productList = new ArrayList<>();
    private ArrayList<CartResponse> cartResponses = new ArrayList<>();
    private ResponseCode responseCode;

    /**
     * Constructor for the presenter
     *
     * @param view, interface for communicating with the view
     * @param model, DataHelper class instance
     */
    ProductCataloguePresenter(@NonNull ProductCatalogueView view,
                              @NonNull DataHelper model) {
        this.view = view;
        this.model = model;
        cartResponses = model.fetchCartResponse();
    }

    /**
     *  update the product list
     */
    void updateProductList() {
        view.updateProductList(getProductList(), responseCode);
    }

    /**
     * Set correct product list
     *
     * @param productList, list of products
     */
    void setProductList(List<Product> productList) {
        this.productList.clear();
        this.productList.addAll(productList);
    }

    /**
     * Get product list
     *
     * @return {@link List<Product>} list of products
     */
    @NonNull
    List<Product> getProductList() {
        return productList;
    }

    /**
     * Get the cart responses
     *
     * @return {@link List<CartResponse>} list of cart responses
     */
    @NonNull
    ArrayList<CartResponse> getCartResponses() {
        return cartResponses;
    }

    /**
     * Get the {@link DataHelper} class which communicates
     * to the API and provides data
     *
     * @return {@link DataHelper} instance
     */
    @NonNull
    DataHelper getProductModel() {
        return model;
    }

    /**
     *  This method add the {@link CartResponse} to the list
     *
     * @param response, {@link CartResponse} which needs to be added to the list
     */
    private void addProductToCartList(CartResponse response) {
        cartResponses.add(response);
    }

    /**
     * This method communicated with the {@link DataHelper} and add product to the cart
     *
     * @param productId, product id of the product which needs to be added
     */
    void addProductToCart(int productId) {
        try {
            view.onProductAddToCart(true, productId);
            model.addProductToCart(productId, responseCallback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Callback response whether product is succesfully added or not
     */
    private Callback<CartResponse> responseCallback = new Callback<CartResponse>() {
        @Override
        public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
            CartResponse cartResponse = response.body();
            view.onProductAddToCart(false, cartResponse.getProductId());
            view.showMessage(true);
            addProductToCartList(cartResponse);
        }

        @Override
        public void onFailure(Call<CartResponse> call, Throwable t) {
            view.onProductAddToCart(false, NullValues.NULL_INT);
            view.showMessage(false);
        }
    };

    /**
     * Save the cart response locally to keep the cart items
     * Although this is not necessary but currently API doesn't provide cart item list.
     * This method ensures the correct state for shopping cart when user kill the application
     * and come back again
     */
    void saveCartResponse() {
        model.saveCartDataLocally(getCartResponses());
    }

    /**
     * When user come back to the application after killing the application, this method fetch
     * the cart responses from the {@link SharedPreferences} and update the cart response list
     */
    void setCartResponseItems() {
        cartResponses = model.fetchCartResponse();
    }

    public void setResponse(ResponseCode error) {
        this.responseCode = error;
    }
}
