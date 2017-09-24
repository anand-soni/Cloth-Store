package com.theclothingstore.mystore.fragments.productcatalogue;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.theclothingstore.mystore.R;
import com.theclothingstore.mystore.fragments.productcatalogue.ProductListViewHolder.OnAddCartListener;
import com.theclothingstore.mystore.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.theclothingstore.mystore.helper.Constants.UnicodeConstants.POUNDS;

/**
 * @author Anand Soni
 */

class ProductListViewAdapter extends RecyclerView.Adapter<ProductListViewHolder> {
    private List<Product> productList = new ArrayList<>();
    private OnAddCartListener listener;

    ProductListViewAdapter(OnAddCartListener listener) {
        this.listener = listener;
    }

    @Override
    public ProductListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getLayout(parent);
        return new ProductListViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(ProductListViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getProductName());
        holder.productCategory.setText(product.getProductCategory());
        holder.addCart.setTag(product.getProductId());
        holder.productPrice.setText(String.format(Locale.getDefault(), "%s%s", POUNDS, String.valueOf(product.getProductPrice())));
        if (product.getProductOldPrice() != null) {
            holder.productOldPrice.setText(String.format(Locale.getDefault(), "%s%s", POUNDS, product.getProductOldPrice()));
            holder.productOldPrice.setPaintFlags(holder.productOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        int stock = product.getProductStock();
        if (stock == 0) {
            holder.outOfStock.setVisibility(VISIBLE);
            holder.addCart.setVisibility(GONE);
            holder.productStock.setVisibility(GONE);
        } else {
            holder.outOfStock.setVisibility(GONE);
            holder.addCart.setVisibility(VISIBLE);
            holder.productStock.setVisibility(VISIBLE);
            holder.productStock.setText(String.format(Locale.getDefault(), "%s", stock));
        }
    }

    void setProductList(List<Product> products) {
        productList.clear();
        productList.addAll(products);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    //for testing purpose
    public View getLayout(ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return inflater.inflate(R.layout.product_item_view, parent, false);
    }

}
















