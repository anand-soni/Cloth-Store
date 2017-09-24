package com.theclothingstore.mystore.fragments.productcatalogue;

import android.support.annotation.NonNull;

import com.theclothingstore.mystore.data.DataHelper;
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

    ProductCataloguePresenter(@NonNull ProductCatalogueView view,
                              @NonNull DataHelper model) {
        this.view = view;
        this.model = model;
        cartResponses = model.fetchCartResponse();
    }

    void updateProductList() {
        view.updateProductList(getProductList());
    }

    void setProductList(List<Product> productList) {
        this.productList.clear();
        this.productList.addAll(productList);
    }

    @NonNull
    List<Product> getProductList() {
        return productList;
    }

    @NonNull
    ArrayList<CartResponse> getCartResponses() {
        return cartResponses;
    }

    @NonNull
    DataHelper getProductModel() {
        return model;
    }

    private void addProductToCartList(CartResponse response) {
        cartResponses.add(response);
    }

    void addProductToCart(int productId) {
        try {
            view.onProductAddToCart(true, productId);
            model.addProductToCart(productId, responseCallback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
            view.showMessage(false);
        }
    };


    void saveCartResponse() {
        model.saveCartDataLocally(getCartResponses());
    }

    void setCartResponseItems() {
        cartResponses = model.fetchCartResponse();
    }
}
