package com.theclothingstore.mystore.fragments.productcatalogue;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.theclothingstore.mystore.R;
import com.theclothingstore.mystore.fragments.BaseFragment;
import com.theclothingstore.mystore.model.CartResponse;
import com.theclothingstore.mystore.model.Product;
import com.theclothingstore.mystore.networktask.AddProductToCartTask;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.theclothingstore.mystore.helper.Constants.APIRequest.FETCH_DATA;
import static com.theclothingstore.mystore.helper.Constants.LoaderId.LOADER_ID_FETCH_DATA;
import static com.theclothingstore.mystore.helper.Constants.StringKeys.API_REQUEST;

/**
 * @author Anand Soni
 */

public class ProductCatalogueFragment extends BaseFragment implements ProductCatalogueView,
        ProductListViewHolder.OnAddCartListener,
        AddProductToCartTask.Response,
        LoaderManager.LoaderCallbacks<ProductCataloguePresenter> {

    @BindView(R.id.product_list)
    RecyclerView productList;

    private ProductCataloguePresenter presenter;
    private ProductListViewAdapter adapter;

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
        presenter = new ProductCataloguePresenter(this, new ProductCatalogueModel(getShopService()));
        // start the loader
        Bundle bundle = new Bundle();
        bundle.putInt(API_REQUEST, FETCH_DATA);
        getLoaderManager().initLoader(LOADER_ID_FETCH_DATA, bundle, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_product, container, false);
        unbinder = ButterKnife.bind(this, root);
        init();
        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shopping_cart:
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
    private void init() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContentActivity());
        productList.setLayoutManager(layoutManager);
        productList.setItemAnimator(new DefaultItemAnimator());
        productList.setAdapter(getProductAdapter());
    }

    /**
     * If {@link RecyclerView} adapter is null then first create it and return the instance of adapter
     *
     * @return {@link ProductListViewAdapter} instance
     */
    private ProductListViewAdapter getProductAdapter() {
        if (adapter == null) {
            adapter = new ProductListViewAdapter(this);
        }
        return adapter;
    }

    /**
     * Update the adapter with new list of products
     *
     * @param products, list of products
     */
    @Override
    public void updateProductList(List<Product> products) {
        getProductAdapter().setProductList(products);
    }

    @Override
    public void onProductAddToCart(boolean running, int productId) {
        adapter.setCartProgress(running, productId);
        adapter.notifyDataSetChanged();
        if (!running) {
            String message = getResources().getString(R.string.cart_add_message);
            showMessage(message);
        }
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


    // loader callback method. called when loader is being created
    @Override
    public Loader<ProductCataloguePresenter> onCreateLoader(int loaderId, Bundle bundle) {
        return new ProductListLoader(getContentActivity(), presenter);
    }

    //loader callback method. called when loader has finished its processing
    @Override
    public void onLoadFinished(android.content.Loader<ProductCataloguePresenter> loader, ProductCataloguePresenter presenter) {
        this.presenter = presenter;
        this.presenter.updateProductList();
    }

    //loader callback method. Called when loader reset
    @Override
    public void onLoaderReset(android.content.Loader<ProductCataloguePresenter> loader) {
        presenter.setProductList(new ArrayList<Product>());
    }

    private void showMessage(String message) {
        Snackbar.make(productList, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onAddCartClicked(View view, int productId) {
        if (view.getId() == R.id.button_add_cart) {
            presenter.updateCartProgress(true, productId);
            new AddProductToCartTask(presenter.getProductModel(), this).execute(productId);
        }
    }

    @Override
    public void onTaskFinish(CartResponse response) {
        presenter.updateCartProgress(false, response.getProductId());
        presenter.addProductToCartList(response);
    }
}
