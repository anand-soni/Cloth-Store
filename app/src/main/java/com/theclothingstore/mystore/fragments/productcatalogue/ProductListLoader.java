package com.theclothingstore.mystore.fragments.productcatalogue;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.NonNull;

import com.theclothingstore.mystore.data.DataHelper;
import com.theclothingstore.mystore.helper.NetworkConnectivityException;
import com.theclothingstore.mystore.helper.ResponseCode;
import com.theclothingstore.mystore.model.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.theclothingstore.mystore.helper.ResponseCode.SUCCESS;

/**
 * @author Anand Soni
 */
class ProductListLoader extends AsyncTaskLoader<ProductCataloguePresenter> {

    private ProductCataloguePresenter presenter;
    private DataHelper model;

    ProductListLoader(@NonNull Context context,
                      @NonNull ProductCataloguePresenter presenter) {
        super(context);
        this.presenter = presenter;
        this.model = presenter.getProductModel();
    }

    @Override
    protected void onStartLoading() {
        if (!presenter.getProductList().isEmpty()) {
            // if product list is not empty then
            // we will use the same presenter
            deliverResult(presenter);
        } else {
            // If we have no data or request is not fetch product, in that case
            // we need to force load
            forceLoad();
        }
    }

    @Override
    public ProductCataloguePresenter loadInBackground() {
        fetchProducts();
        //return the presenter
        return presenter;
    }

    private void fetchProducts() {
        List<Product> productList = new ArrayList<>();
        try {
            // Synchronous operation for fetching all the products using Shop API
            productList = model.getAllProducts();
            presenter.setResponse(SUCCESS);
        } catch (IOException e) {
            if(e instanceof NetworkConnectivityException) {
                presenter.setResponse(ResponseCode.NETWORK_ERROR);
            }
            e.printStackTrace();
        }

        //set the product list for updating the view
        presenter.setProductList(productList);
    }

    @Override
    public void deliverResult(ProductCataloguePresenter presenter) {
        // keep the presenter for later retrieval
        this.presenter = presenter;
        //deliver the result
        super.deliverResult(presenter);
    }
}
