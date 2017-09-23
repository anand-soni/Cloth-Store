package com.theclothingstore.mystore.fragments.productcatalogue;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.theclothingstore.mystore.R;
import com.theclothingstore.mystore.fragments.BaseFragment;
import com.theclothingstore.mystore.model.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Anand Soni
 */

public class ProductCatalogueFragment extends BaseFragment implements ProductCatalogueView,
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
        presenter = new ProductCataloguePresenter(this, new ProductCatalogueModel(getShopService()));
        // start the loader
        getLoaderManager().initLoader(0, null, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_product, container, false);
        unbinder = ButterKnife.bind(this, root);
        init();
        return root;
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
            adapter = new ProductListViewAdapter();
        }
        return adapter;
    }

    /**
     * Update the adapter with new list of products
     *
     * @param products, list of products
     */
    @Override
    public void updateView(List<Product> products) {
        getProductAdapter().setProductList(products);
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
    public Loader<ProductCataloguePresenter> onCreateLoader(int i, Bundle bundle) {
        return new CataloguePresenterLoader(getContentActivity(), presenter);
    }

    //loader callback method. called when loader has finished its processing
    @Override
    public void onLoadFinished(android.content.Loader<ProductCataloguePresenter> loader, ProductCataloguePresenter presenter) {
        this.presenter = presenter;
        this.presenter.updateView();
    }

    //loader callback method. Called when loader reset
    @Override
    public void onLoaderReset(android.content.Loader<ProductCataloguePresenter> loader) {
        presenter.setProductList(new ArrayList<Product>());
    }
}
