package com.theclothingstore.mystore.fragments.productcatalogue;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.theclothingstore.mystore.R;
import com.theclothingstore.mystore.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Anand Soni
 */

class ProductListViewAdapter extends RecyclerView.Adapter<ProductListViewHolder> {
    private List<Product> productList = new ArrayList<>();

    void setProductList(List<Product> products) {
        productList.clear();
        productList.addAll(products);
        this.notifyDataSetChanged();
    }

    @Override
    public ProductListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.product_item_view, parent, false);
        return new ProductListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductListViewHolder holder, int position) {
        String pounds = "\u00a3";
        Product product = productList.get(position);
        holder.productName.setText(product.getName());
        holder.productCategory.setText(product.getCategory());
        holder.productPrice.setText(String.format(Locale.getDefault(), "%s%s", pounds, String.valueOf(product.getPrice())));
        if (product.getOldPrice() != null) {
            holder.productOldPrice.setText(String.format(Locale.getDefault(), "%s%s", pounds, product.getOldPrice()));
            holder.productOldPrice.setPaintFlags(holder.productOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        int stock = product.getStock();
        if (stock == 0) {
            holder.outOfStock.setVisibility(View.VISIBLE);
        } else {
            holder.outOfStock.setVisibility(View.GONE);
            holder.productStock.setVisibility(View.VISIBLE);
            holder.productStock.setText(String.format(Locale.getDefault(), "%s", stock));
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
















