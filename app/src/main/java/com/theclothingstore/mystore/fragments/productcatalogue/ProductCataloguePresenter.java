package com.theclothingstore.mystore.fragments.productcatalogue;

import android.support.annotation.NonNull;

import com.theclothingstore.mystore.model.CartResponse;
import com.theclothingstore.mystore.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anand Soni
 */

public class ProductCataloguePresenter {

    private ProductCatalogueView view;
    private ProductCatalogueModel model;

    private List<Product> productList = new ArrayList<>();
    private List<CartResponse> cartItems = new ArrayList<>();

    ProductCataloguePresenter(@NonNull ProductCatalogueView view,
                              @NonNull ProductCatalogueModel model) {
        this.view = view;
        this.model = model;
    }

    void updateProductList() {
        view.updateProductList(getProductList());
    }

    void setProductList(List<Product> productList) {
        this.productList.clear();
        this.productList.addAll(productList);
    }

    List<Product> getProductList() {
        return productList;
    }

    List<CartResponse> getCartItemList() {
        return cartItems;
    }

    ProductCatalogueModel getProductModel() {
        return model;
    }

    void addProductToCartList(CartResponse response) {
        cartItems.add(response);
    }

    void updateCartProgress(boolean running, int productId) {
        view.onProductAddToCart(running, productId);
    }
}
