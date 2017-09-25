package com.theclothingstore.mystore.fragments.productcatalogue;

import com.theclothingstore.mystore.helper.ResponseCode;
import com.theclothingstore.mystore.model.Product;

import java.util.List;

/**
 * @author Anand Soni
 */

interface ProductCatalogueView {
    void updateProductList(List<Product> productList, ResponseCode code);

    void onProductAddToCart(boolean running, int productId);

    void showMessage(boolean success);
}
