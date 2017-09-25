package com.theclothingstore.mystore.fragments.productcatalogue;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.theclothingstore.mystore.R;
import com.theclothingstore.mystore.data.DataHelper;
import com.theclothingstore.mystore.fragments.BaseFragment;
import com.theclothingstore.mystore.helper.ResponseCode;
import com.theclothingstore.mystore.model.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.theclothingstore.mystore.helper.Constants.LoaderId.LOADER_ID_FETCH_DATA;

/**
 * @author Anand Soni
 */

public class ProductCatalogueFragment extends BaseFragment implements ProductCatalogueView,
        ProductListViewHolder.OnAddCartListener,
        LoaderManager.LoaderCallbacks<ProductCataloguePresenter> {

    @BindView(R.id.product_list)
    RecyclerView productList;
    @BindView(R.id.progress_bar)
    ProgressBar progress;
    @BindView(R.id.button_retry)
    Button retry;
    @BindView(R.id.text_view_no_connection)
    TextView noConnection;
    private ProductListViewAdapter adapter;
    private ProductCataloguePresenter presenter;

    /**
     * Static method which returns the {@link ProductCatalogueFragment} instance
     *
     * @return {@link ProductCatalogueFragment} instance
     */
    public static ProductCatalogueFragment newInstance() {
        return new ProductCatalogueFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        presenter = new ProductCataloguePresenter(this, new DataHelper(getShopService(), getSharedPreference()));
        // start the loader
        getLoaderManager().initLoader(LOADER_ID_FETCH_DATA, null, this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_product, container, false);
        unbinder = ButterKnife.bind(this, root);
        initialize();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setCartResponseItems();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shopping_cart:
                presenter.saveCartResponse();
                getNavigationController().openShoppingCartScreen();
                return true;
            case R.id.wish_list:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * initialize the recycler view and adapter
     */
    @VisibleForTesting
    public void initialize() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContentActivity());
        productList.setLayoutManager(layoutManager);
        productList.setItemAnimator(new DefaultItemAnimator());
        adapter = new ProductListViewAdapter(this);
        productList.setAdapter(adapter);
    }

    /**
     * Update the adapter with new list of products
     *
     * @param products, list of products
     */
    @Override
    public void updateProductList(List<Product> products, ResponseCode code) {
        progress.setVisibility(View.GONE);
        if(code == ResponseCode.NETWORK_ERROR) {
            productList.setVisibility(View.GONE);
            noConnection.setVisibility(View.VISIBLE);
            retry.setVisibility(View.VISIBLE);
        }  else {
            productList.setVisibility(View.VISIBLE);
            adapter.setProductList(products);
            adapter.notifyDataSetChanged();
            noConnection.setVisibility(View.GONE);
            retry.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.button_retry)
    public void onRetryButtonClick() {
        getLoaderManager().restartLoader(LOADER_ID_FETCH_DATA, null, this);
    }

    @Override
    public void onProductAddToCart(boolean running, int productId) {
        showProgress(running);
        adapter.notifyDataSetChanged();
    }


    // loader callback method. called when loader is being created
    @Override
    public Loader<ProductCataloguePresenter> onCreateLoader(int loaderId, Bundle bundle) {
        return new ProductListLoader(getContentActivity(), presenter);
    }

    // loader callback method. called when loader has finished its processing
    @Override
    public void onLoadFinished(android.content.Loader<ProductCataloguePresenter> loader, ProductCataloguePresenter presenter) {
        this.presenter = presenter;
        this.presenter.updateProductList();
    }

    // loader callback method. Called when loader reset
    @Override
    public void onLoaderReset(android.content.Loader<ProductCataloguePresenter> loader) {
        presenter.setProductList(new ArrayList<Product>());
    }

    @Override
    public boolean onBackPressed() {
        presenter.saveCartResponse();
        return super.onBackPressed();
    }


    @Override
    public void onAddCartClicked(View view, int productId) {
        if (view.getId() == R.id.button_add_cart) {
            presenter.addProductToCart(productId);
        }
    }

    /**
     * This method show the correct message to the user according to success or
     * failure response
     *
     * @param success, boolean for success or failure
     */
    @Override
    public void showMessage(boolean success) {
        String message;
        if (success) {
            message = getResources().getString(R.string.cart_add_message_success);
        } else {
            message = getResources().getString(R.string.message_fail);
        }
        showMessage(message);
    }

    /**
     * Get the {@link Toolbar} title
     *
     * @return {@link String} instance
     */
    @NonNull
    @Override
    protected String getToolbarTitle() {
        return getResources().getString(R.string.app_name);
    }

    /**
     * for testing purpose, returns the adapter
     *
     * @return {@link ProductListViewAdapter} instance
     */
    public ProductListViewAdapter getAdapter() {
        return adapter;
    }

    /**
     * for testing purpose, returns the {@link RecyclerView} which displays product list
     *
     * @return {@link RecyclerView} instance
     */
    public RecyclerView getProductList() {
        return productList;
    }

    /**
     * for testing purpose, returns the {@link ProductCataloguePresenter} which displays product list
     *
     * @return {@link ProductCataloguePresenter} instance
     */
    public ProductCataloguePresenter getPresenter() {
        return presenter;
    }

}
