package com.theclothingstore.mystore.data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Anand Soni
 */

public class ShopService {

    private static String baseUrl = "https://private-anon-18f3559914-ddshop.apiary-mock.com/";
    private ShopAPI shopAPI;

    public ShopService() {
        this(baseUrl);
    }

    private ShopService(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        shopAPI = retrofit.create(ShopAPI.class);
    }

    public ShopAPI getShopAPI() {
        return shopAPI;
    }
}
