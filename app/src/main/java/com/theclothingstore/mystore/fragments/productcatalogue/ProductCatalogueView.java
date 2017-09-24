package com.theclothingstore.mystore.fragments.productcatalogue;

import com.theclothingstore.mystore.model.Product;

import java.util.List;

/**
 * @author Anand Soni
 */

interface ProductCatalogueView {
    void updateProductList(List<Product> productList);

    void onProductAddToCart(boolean running, int productId);

    void showMessage(boolean success);
}
