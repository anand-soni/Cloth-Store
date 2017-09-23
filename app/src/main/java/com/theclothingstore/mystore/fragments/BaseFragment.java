package com.theclothingstore.mystore.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.theclothingstore.mystore.ContentActivity;
import com.theclothingstore.mystore.R;
import com.theclothingstore.mystore.controllers.NavigationController;
import com.theclothingstore.mystore.data.ShopService;
import com.theclothingstore.mystore.helper.NullValues;

import butterknife.Unbinder;

/**
 * @author Anand Soni
 */

public class BaseFragment extends Fragment implements Toolbar.OnMenuItemClickListener {

    public static final String TAG = BaseFragment.class.getSimpleName();

    protected ContentActivity contentActivity;
    protected Unbinder unbinder;

    /**
     * Default navigation listener for toolbar
     */
    private final View.OnClickListener DEFAULT_TOOLBAR_NAVIGATION = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            contentActivity.onBackPressed();
        }
    };

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (contentActivity != null) {
            // if contentActivity is not null, then return to keep the injected reference
            return;
        }

        setContentActivity(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateToolBar();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    /**
     * This method is responsible for setting the ContentActivity instance
     *
     * @param context, Activity context
     */

    private void setContentActivity(@NonNull Context context) {
        if (context instanceof ContentActivity) {
            this.contentActivity = (ContentActivity) context;
        } else if (getActivity() instanceof ContentActivity) {
            this.contentActivity = (ContentActivity) getActivity();
        } else {
            Log.e(TAG, "Invalid activity type");
        }
    }

    /**
     * This method is responsible for setting the ContentActivity instance if not already set
     * otherwise return the instance
     *
     * @return ContentActivity Instance
     */
    @NonNull
    public ContentActivity getContentActivity() {
        if (contentActivity == null) {
            setContentActivity(getActivity());
        }
        return contentActivity;
    }

    /**
     * This method is responsible for updating the toolbar.
     */
    protected void updateToolBar() {
        final Toolbar toolbar = getContentActivity().getToolbar();
        if (toolbar != null) {
            toolbar.setTitleTextColor(ContextCompat.getColor(contentActivity.getApplicationContext(), getTitleTextColor()));
            toolbar.setOnMenuItemClickListener(this);
            toolbar.setNavigationIcon(isToolbarNavigationActive() ? getNavigationDrawable() : null);
            toolbar.setNavigationOnClickListener(getNavigationOnClickListener());
            toolbar.setTitle(getToolbarTitle());
        } else {
            Log.w(TAG, "Action bar has been not initialized yet.");
        }
    }

    /**
     * Override to specify different menu for the fragment
     *
     * @return int, menu id
     */
    protected int getMenu() {
        return R.menu.empty_menu;
    }

    /**
     * Returns the navigation drawable
     *
     * @return Drawable instance
     */
    protected Drawable getNavigationDrawable() {
        return ContextCompat.getDrawable(contentActivity, getNavigationIcon());
    }

    /**
     * Return TRUE to activate Toolbar navigation chevron.
     *
     * @return TRUE to activate Toolbar navigation chevron.
     */
    protected boolean isToolbarNavigationActive() {
        return true;
    }

    /**
     * Use this to disable back button. The default behaviour of onBackPressed is based on the isBackEnabled
     */
    protected boolean isBackEnabled() {
        return true;
    }

    /**
     * Override to specify screen specific Toolbar title.
     *
     * @return string to use for Toolbar title.
     */
    @NonNull
    protected String getToolbarTitle() {
        return NullValues.NULL_STRING;
    }

    /**
     * Default navigation icon for all fragments
     *
     * @return drawable id to use for toolbar navigation icon
     */

    protected int getNavigationIcon() {
        return R.drawable.back;
    }


    /**
     * Override to specify back press is enabled or not
     *
     * @return TRUE or FALSE
     */
    public boolean onBackPressed() {
        return isBackEnabled();
    }

    /**
     * Override to specify different color for title text
     *
     * @return int, color id
     */
    protected int getTitleTextColor() {
        return android.R.color.white;
    }

    /**
     * Returns the default toolbar navigation listener
     *
     * @return OnClickListener instance
     */
    @Nullable
    protected View.OnClickListener getNavigationOnClickListener() {
        return isToolbarNavigationActive() ? DEFAULT_TOOLBAR_NAVIGATION : null;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

    /**
     * This method returns the NavigationController instance
     *
     * @return NavigationController instance
     */
    @NonNull
    public NavigationController getNavigationController() {
        return contentActivity.getNavigationController();
    }

    /**
     * This method returns the instance of ShopService
     *
     * @return ShopService instance
     */
    public ShopService getShopService() {
        return getContentActivity().getShopService();
    }
}
