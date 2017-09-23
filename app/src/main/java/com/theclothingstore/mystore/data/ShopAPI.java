package com.theclothingstore.mystore.data;

import com.theclothingstore.mystore.model.CartResponse;
import com.theclothingstore.mystore.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author Anand Soni
 */

public interface ShopAPI {

    @GET("products")
    Call<List<Product>> getAllProducts();

    @GET("products/{id}")
    Call<Product> getProduct(@Path("productId") int productId);

    @POST("cart")
    Call<CartResponse> addProductToCart(@Body int productId);

    @DELETE("cart/{id}")
    Call<Void> removeProductFromCart(@Path("id") int cardId);
}
