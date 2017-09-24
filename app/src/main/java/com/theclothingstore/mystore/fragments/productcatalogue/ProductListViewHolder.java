package com.theclothingstore.mystore.fragments.productcatalogue;

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

class ProductListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.product_name)
    TextView productName;
    @BindView(R.id.product_category)
    TextView productCategory;
    @BindView(R.id.product_price)
    TextView productPrice;
    @BindView(R.id.product_old_price)
    TextView productOldPrice;
    @BindView(R.id.text_view_out_of_stock)
    TextView outOfStock;
    @BindView(R.id.product_stock)
    TextView productStock;
    @BindView(R.id.button_add_cart)
    Button addCart;

    private OnAddCartListener listener;

    ProductListViewHolder(View itemView, OnAddCartListener listener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.listener = listener;
    }

    @OnClick(R.id.button_add_cart)
    void onAddCartClick(View view) {
        listener.onAddCartClicked(view, Integer.parseInt(String.valueOf(view.getTag())));
    }

    interface OnAddCartListener {
        void onAddCartClicked(View view, int productId);
    }
}
