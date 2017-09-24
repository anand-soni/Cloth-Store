package com.theclothingstore.mystore.fragments.shopingcart;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.theclothingstore.mystore.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Anand Soni
 */

public class CartListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.product_name)
    TextView productName;
    @BindView(R.id.product_price)
    TextView productPrice;
    @BindView(R.id.button_remove_cart)
    Button removeCart;

    private final OnCartRemoveListener listener;

    CartListViewHolder(View itemView, OnCartRemoveListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.listener = listener;
    }

    @OnClick(R.id.button_remove_cart)
    void onRemoveCartClick(View view) {
        listener.onCartItemRemove(view, Integer.parseInt(String.valueOf(view.getTag())));
    }

    interface OnCartRemoveListener {
        void onCartItemRemove(View view, int cartId);
    }

}
