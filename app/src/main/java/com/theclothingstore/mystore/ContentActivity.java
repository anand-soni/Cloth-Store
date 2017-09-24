package com.theclothingstore.mystore;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;

import com.theclothingstore.mystore.controllers.NavigationController;
import com.theclothingstore.mystore.data.ShopService;
import com.theclothingstore.mystore.helper.Constants;

public class ContentActivity extends AppCompatActivity {

    private RelativeLayout root;

    //main container where fragments can be added and replaced
    public static final int CONTAINER_ID = R.id.container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        setSupportActionBar(getToolbar());
        root = findViewById(R.id.main_content);
        openProductCatalogueScreen();
    }

    /**
     * open product catalogue screen
     */
    public void openProductCatalogueScreen() {
        getNavigationController().openProductCatalogueScreen();
    }

    /**
     * Global method for showing message to user on any fragment screen
     *
     * @param message, message which needs to be conveyed
     */
    public void showMessage(String message) {
        Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show();
    }

    /**
     * Returns Toolbar instance of this activity.
     *
     * @return Toolbar instance.
     */
    @Nullable
    public Toolbar getToolbar() {
        return (Toolbar) findViewById(R.id.toolbar);
    }

    /**
     * This method returns the navigation contorller which is responsible for all the navigation
     * inside our application.
     *
     * @return NavigationController instance
     */
    @NonNull
    public NavigationController getNavigationController() {
        return new NavigationController(this);
    }

    /**
     * This method returns the application instance
     *
     * @return MyStoreApplication instance
     */
    @NonNull
    public MyStoreApplication getMyStoreApplication() {
        return (MyStoreApplication) getApplicationContext();
    }

    /**
     * This method returns the instance of ShopService
     *
     * @return ShopService instance
     */
    @NonNull
    public ShopService getShopService() {
        return getMyStoreApplication().getShopService();
    }

    /**
     * This method returns the {@link SharedPreferences} instance
     *
     * @return {@link SharedPreferences} instance
     */
    public SharedPreferences getSharedPreference() {
        return getSharedPreferences(Constants.FileName.SHARED_PREFERENCE_FILE, MODE_PRIVATE);
    }

}
