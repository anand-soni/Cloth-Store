package com.theclothingstore.mystore.fragments.productcatalogue;

import com.theclothingstore.mystore.model.Product;

import java.util.List;

/**
 * @author Anand Soni
 */

interface ProductCatalogueView {
    void updateView(List<Product> productList);
}
