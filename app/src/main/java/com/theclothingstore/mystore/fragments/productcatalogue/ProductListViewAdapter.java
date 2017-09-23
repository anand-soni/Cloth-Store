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
    private boolean running = false;
    private int productId;

    ProductListViewAdapter(OnAddCartListener listener) {
        this.listener = listener;
    }

    void setProductList(List<Product> products) {
        productList.clear();
        productList.addAll(products);
        this.notifyDataSetChanged();
    }

    void setCartProgress(boolean running, int productId) {
        this.running = running;
        this.productId = productId;
        this.notifyDataSetChanged();
    }

    @Override
    public ProductListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.product_item_view, parent, false);
        return new ProductListViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(ProductListViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productName.setText(product.getProductName());
        holder.productCategory.setText(product.getProductCategory());
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
            holder.addCart.setTag(product.getProductId());
            holder.productStock.setVisibility(VISIBLE);
            holder.productStock.setText(String.format(Locale.getDefault(), "%s", stock));
        }

        int itemProductId = Integer.parseInt(String.valueOf(holder.addCart.getTag()));

        if (running && itemProductId == productId) {
            holder.progress.setVisibility(VISIBLE);
            holder.addCart.setVisibility(GONE);
        } else {
            holder.progress.setVisibility(GONE);
            holder.addCart.setVisibility(VISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }
}
















