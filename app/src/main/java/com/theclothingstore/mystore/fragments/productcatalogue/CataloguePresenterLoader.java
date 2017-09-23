package com.theclothingstore.mystore.fragments.productcatalogue;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.NonNull;

import com.theclothingstore.mystore.model.Product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class helps to resolve most common problem of surviving the orientation change.
 * Loader can survive the orientation change. CataloguePresenterLoader is the Subclass of AsyncTaskLoader
 * which keeps presenter and it's data safe during orientation change.
 *
 * @author Anand Soni
 */
class CataloguePresenterLoader extends AsyncTaskLoader<ProductCataloguePresenter> {

    private ProductCataloguePresenter presenter;

    CataloguePresenterLoader(@NonNull Context context,
                             @NonNull ProductCataloguePresenter presenter) {
        super(context);
        this.presenter = presenter;
    }

    @Override
    protected void onStartLoading() {
        if (!presenter.getProductList().isEmpty()) {
            // if product list is not empty then
            // we will use the same presenter
            deliverResult(presenter);
        } else {
            // We have no data, so start loading the data
            forceLoad();
        }
    }

    @Override
    public ProductCataloguePresenter loadInBackground() {
        // Get the model from the presenter
        ProductCatalogueModel model = presenter.getProductModel();
        List<Product> productList = new ArrayList<>();
        try {
            // Synchronous operation for fetching all the products using Shop API
            productList = model.getAllProducts();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //set the product list for updating the view
        presenter.setProductList(productList);

        //return the presenter
        return presenter;
    }

    @Override
    public void deliverResult(ProductCataloguePresenter presenter) {
        // keep the presenter for later retrieval
        this.presenter = presenter;
        //deliver the result
        super.deliverResult(presenter);
    }
}
