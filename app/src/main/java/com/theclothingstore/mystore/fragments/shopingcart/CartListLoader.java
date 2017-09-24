package com.theclothingstore.mystore.fragments.shopingcart;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.NonNull;

import com.theclothingstore.mystore.data.DataHelper;
import com.theclothingstore.mystore.model.CartItem;
import com.theclothingstore.mystore.model.CartResponse;
import com.theclothingstore.mystore.model.Product;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Anand Soni
 */

public class CartListLoader extends AsyncTaskLoader<ShoppingCartPresenter> {

    private ShoppingCartPresenter presenter;
    private DataHelper model;

    public CartListLoader(@NonNull Context context,
                          @NonNull ShoppingCartPresenter presenter) {
        super(context);
        this.presenter = presenter;
        this.model = presenter.getProductModel();
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ShoppingCartPresenter loadInBackground() {
        fetchCartItems();
        //return the presenter
        return presenter;
    }

    private void fetchCartItems() {
        CopyOnWriteArrayList<CartResponse> cartResponses = new CopyOnWriteArrayList<>(presenter
                .getCartResponseItems());
        Product product = null;
        for (CartResponse response : cartResponses) {
            try {
                // Synchronous operation for fetching single product using Shop API
                product = model.getProduct(response.getProductId());
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (product != null) {
                CartItem cartItem = new CartItem();
                cartItem.setProduct(product);
                cartItem.setCartId(response.getCartId());
                presenter.addCartItems(cartItem);
            }
        }
    }

    @Override
    public void deliverResult(ShoppingCartPresenter presenter) {
        // keep the presenter for later retrieval
        this.presenter = presenter;
        //deliver the result
        super.deliverResult(presenter);
    }
}
