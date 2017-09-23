package com.theclothingstore.mystore.networktask;

import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.theclothingstore.mystore.fragments.productcatalogue.ProductCatalogueModel;
import com.theclothingstore.mystore.model.CartResponse;

import java.io.IOException;

/**
 * @author Anand Soni
 */

public class AddProductToCartTask extends AsyncTask<Integer, Void, CartResponse> {

    private final ProductCatalogueModel model;
    private final Response response;

    public AddProductToCartTask(@NonNull ProductCatalogueModel model,
                                @NonNull Response response) {
        this.model = model;
        this.response = response;
    }

    @Override
    protected CartResponse doInBackground(Integer... integers) {
        CartResponse response = null;
        int productId = integers[0];
        try {
            response = model.addProductToCart(productId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(CartResponse cartResponse) {
        response.onTaskFinish(cartResponse);
    }

    public interface Response {
        void onTaskFinish(CartResponse response);
    }
}
