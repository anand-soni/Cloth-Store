package com.theclothingstore.mystore.fragments.productcatalogue;

import android.support.annotation.NonNull;

import com.theclothingstore.mystore.data.ShopService;
import com.theclothingstore.mystore.model.CartResponse;
import com.theclothingstore.mystore.model.Product;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * @author Anand Soni
 */

public class ProductCatalogueModel {

    private ShopService service;

    ProductCatalogueModel(@NonNull ShopService service) {
        this.service = service;
    }

    List<Product> getAllProducts() throws IOException {
        Call<List<Product>> call = service.getShopAPI().getAllProducts();
        Response<List<Product>> response = call.execute();
        return response.body();
    }


    public CartResponse addProductToCart(int productId) throws IOException {
        Call<CartResponse> call = service.getShopAPI().addProductToCart(productId);
        Response<CartResponse> response = call.execute();
        return response.body();
    }

    boolean removeProductFromCart(int cartId) throws IOException {
        Call<Void> call = service.getShopAPI().removeProductFromCart(cartId);
        Response response = call.execute();
        return response.isSuccessful();
    }
}
