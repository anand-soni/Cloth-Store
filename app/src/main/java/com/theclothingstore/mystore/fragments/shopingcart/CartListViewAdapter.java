package com.theclothingstore.mystore.fragments.shopingcart;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.theclothingstore.mystore.R;
import com.theclothingstore.mystore.fragments.shopingcart.CartListViewHolder.OnCartRemoveListener;
import com.theclothingstore.mystore.model.CartItem;
import com.theclothingstore.mystore.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.theclothingstore.mystore.helper.Constants.UnicodeConstants.POUNDS;

/**
 * @author Anand Soni
 */

class CartListViewAdapter extends RecyclerView.Adapter<CartListViewHolder> {

    private final OnCartRemoveListener listener;
    private List<CartItem> cartList = new ArrayList<>();

    CartListViewAdapter(@NonNull OnCartRemoveListener listener) {
        this.listener = listener;
    }

    @Override
    public CartListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cart_item_view, parent, false);
        return new CartListViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(CartListViewHolder holder, int position) {
        CartItem cartItem = cartList.get(position);
        Product product = cartItem.getProduct();
        holder.productName.setText(product.getProductName());
        holder.removeCart.setTag(cartItem.getCartId());
        holder.productPrice.setText(String.format(Locale.getDefault(), "%s%s", POUNDS, String.valueOf(product.getProductPrice())));
    }

    void setCartItems(List<CartItem> cartItems) {
        cartList = cartItems;
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }
}
