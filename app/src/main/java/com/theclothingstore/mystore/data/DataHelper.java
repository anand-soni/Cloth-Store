package com.theclothingstore.mystore.data;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.theclothingstore.mystore.helper.NullValues;
import com.theclothingstore.mystore.model.CartResponse;
import com.theclothingstore.mystore.model.Product;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.theclothingstore.mystore.helper.Constants.StringKeys.CART_RESPONSE_LIST;

/**
 * @author Anand Soni
 */

public class DataHelper {

    private ShopService service;
    private SharedPreferences preferences;

    public DataHelper(@NonNull ShopService service,
                      @NonNull SharedPreferences preference) {
        this.service = service;
        this.preferences = preference;
    }

    public List<Product> getAllProducts() throws IOException {
        Call<List<Product>> call = service.getShopAPI().getAllProducts();
        Response<List<Product>> response = call.execute();
        return response.body();
    }

    public Product getProduct(int productId) throws IOException {
        Call<Product> call = service.getShopAPI().getProduct(productId);
        Response<Product> response = call.execute();
        return response.body();
    }

    public void addProductToCart(int productId, Callback<CartResponse> callback) throws IOException {
        Call<CartResponse> call = service.getShopAPI().addProductToCart(productId);
        call.enqueue(callback);
    }

    public void removeProductFromCart(int cartId, Callback<Void> callback) throws IOException {
        Call<Void> call = service.getShopAPI().removeProductFromCart(cartId);
        call.enqueue(callback);
    }

    public void saveCartDataLocally(@NonNull List<CartResponse> responses) {
        Gson gson = new Gson();
        Type type = new TypeToken<List<CartResponse>>() {
        }.getType();
        String cartResponse = gson.toJson(responses, type);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(CART_RESPONSE_LIST, cartResponse);
        editor.apply();
    }

    public ArrayList<CartResponse> fetchCartResponse() {
        String cartResponse = preferences.getString(CART_RESPONSE_LIST, NullValues.NULL_STRING);
        Gson gson = new Gson();
        Type type = new TypeToken<List<CartResponse>>() {
        }.getType();
        ArrayList<CartResponse> responses = gson.fromJson(cartResponse, type);
        if (responses == null) {
            return new ArrayList<>();
        } else {
            return responses;
        }
    }

}
