package com.theclothingstore.mystore;


import android.app.Application;

import com.theclothingstore.mystore.data.ShopService;

/**
 * @author Anand Soni
 */

public class MyStoreApplication extends Application {

    private ShopService shopService;

    @Override
    public void onCreate() {
        super.onCreate();
        shopService = new ShopService(getApplicationContext());
    }

    public ShopService getShopService() {
        return shopService;
    }
}
