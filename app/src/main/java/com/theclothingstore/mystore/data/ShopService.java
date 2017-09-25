package com.theclothingstore.mystore.data;

import android.content.Context;
import android.support.annotation.NonNull;

import com.theclothingstore.mystore.helper.NetworkInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Anand Soni
 */

public class ShopService {

    private static String baseUrl = "https://private-anon-18f3559914-ddshop.apiary-mock.com/";
    private final Context context;
    private ShopAPI shopAPI;

    public ShopService(@NonNull Context context) {
        this.context = context;
        shopAPI = setShopAPI(baseUrl);
    }

    private ShopAPI setShopAPI(String baseUrl) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new NetworkInterceptor(context)).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ShopAPI.class);
    }

    public ShopAPI getShopAPI() {
        return shopAPI;
    }
}
