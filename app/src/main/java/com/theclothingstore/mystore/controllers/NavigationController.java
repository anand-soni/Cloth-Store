package com.theclothingstore.mystore.controllers;

import android.support.annotation.NonNull;
import android.util.Log;

import com.theclothingstore.mystore.ContentActivity;
import com.theclothingstore.mystore.fragments.BaseFragment;
import com.theclothingstore.mystore.fragments.WelcomeFragment;

/**
 * @author Anand Soni
 */

public class NavigationController {

    private static final String TAG = NavigationController.class.getSimpleName();

    @NonNull
    private ContentActivity contentActivity;

    public NavigationController(@NonNull ContentActivity contentActivity) {
        this.contentActivity = contentActivity;
    }

    @NonNull
    private ContentActivity getContentActivity(){
        return contentActivity;
    }

    public void openWelcomeScreen() {
        WelcomeFragment fragment = WelcomeFragment.newInstance();
        doFragmentTransaction(fragment);
    }

    private void doFragmentTransaction(@NonNull BaseFragment baseFragment){
        doFragmentTransaction(baseFragment, false);
    }

    private void doFragmentTransaction(@NonNull BaseFragment baseFragment, boolean addToBackStack){
        android.app.FragmentManager manager = getContentActivity().getFragmentManager();
        android.app.FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(ContentActivity.CONTAINER_ID, baseFragment, getFragmentName(baseFragment.getClass()));
        if(addToBackStack) {
            transaction.addToBackStack(getFragmentName(baseFragment.getClass()));
        }
        transaction.commit();
    }

    @NonNull
    private String getFragmentName(@NonNull Class fragmentClass) {
        return fragmentClass.getSimpleName();
    }

}
