package com.theclothingstore.mystore.fragments.productcatalogue;

import android.support.annotation.NonNull;

import com.theclothingstore.mystore.data.ShopService;
import com.theclothingstore.mystore.model.Product;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @author Anand Soni
 */

class ProductCatalogueModel {

    private ShopService service;

    ProductCatalogueModel(@NonNull ShopService service) {
        this.service = service;
    }

    List<Product> getAllProducts() throws IOException {
        Call<List<Product>> products = service.getShopAPI().getAllProducts();
        Response<List<Product>> response = products.execute();
        return response.body();
    }


}
