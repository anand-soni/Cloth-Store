package com.theclothingstore.mystore.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.theclothingstore.mystore.R;

/**
 * @author Anand Soni
 */

public class WelcomeFragment extends BaseFragment{

    public static WelcomeFragment newInstance(){
        return new WelcomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_welcome_screen, container, false);
    }

    @Override
    protected boolean isToolbarNavigationActive() {
        return true;
    }

    @NonNull
    @Override
    protected String getToolbarTitle() {
        return getResources().getString(R.string.app_name);
    }
}
