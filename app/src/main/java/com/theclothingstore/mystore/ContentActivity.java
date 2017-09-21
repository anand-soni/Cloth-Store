package com.theclothingstore.mystore;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.theclothingstore.mystore.controllers.NavigationController;

public class ContentActivity extends AppCompatActivity {

    //main container where fragments can be added and replaced
    public static final int CONTAINER_ID = R.id.container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        setSupportActionBar(getToolbar());
        getNavigationController().openWelcomeScreen();
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
    public NavigationController getNavigationController(){
        return new NavigationController(this);
    }

}
