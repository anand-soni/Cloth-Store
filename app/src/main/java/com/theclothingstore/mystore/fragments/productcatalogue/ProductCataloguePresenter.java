package com.theclothingstore.mystore.fragments.productcatalogue;

import android.support.annotation.NonNull;

import com.theclothingstore.mystore.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Anand Soni
 */

class ProductCataloguePresenter {

    private ProductCatalogueView view;
    private ProductCatalogueModel model;

    private List<Product> productList = new ArrayList<>();

    ProductCataloguePresenter(@NonNull ProductCatalogueView view,
                              @NonNull ProductCatalogueModel model) {
        this.view = view;
        this.model = model;
    }

    void updateView() {
        view.updateView(getProductList());
    }

    void setProductList(List<Product> productList) {
        this.productList.clear();
        this.productList.addAll(productList);
    }

    List<Product> getProductList() {
        return productList;
    }

    ProductCatalogueModel getProductModel() {
        return model;
    }
}
