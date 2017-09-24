package com.theclothingstore.mystore.fragments.shopingcart;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.theclothingstore.mystore.R;
import com.theclothingstore.mystore.data.DataHelper;
import com.theclothingstore.mystore.fragments.BaseFragment;
import com.theclothingstore.mystore.model.CartItem;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.theclothingstore.mystore.helper.Constants.LoaderId.LOADER_ID_CART_DATA;
import static com.theclothingstore.mystore.helper.Constants.UnicodeConstants.POUNDS;

/**
 * @author Anand Soni
 */

public class ShoppingCartFragment extends BaseFragment implements CartListViewHolder.OnCartRemoveListener,
        LoaderManager.LoaderCallbacks<ShoppingCartPresenter>,
        ShoppingCartView {

    @BindView(R.id.cart_list)
    RecyclerView cartList;
    @BindView(R.id.text_view_total_price)
    TextView totalCartPrice;
    @BindView(R.id.progress_bar)
    ProgressBar progress;
    @BindView(R.id.text_view_empty)
    TextView emptyCartMessage;

    private ShoppingCartPresenter presenter;
    private CartListViewAdapter adapter;

    public static ShoppingCartFragment newInstance() {
        return new ShoppingCartFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ShoppingCartPresenter(new DataHelper(getShopService(), getSharedPreference()), this);
        getLoaderManager().initLoader(LOADER_ID_CART_DATA, null, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        unbinder = ButterKnife.bind(this, root);
        initialize();
        return root;
    }

    /**
     * initialize the recycler view and adapter
     */
    private void initialize() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContentActivity());
        cartList.setLayoutManager(layoutManager);
        cartList.setItemAnimator(new DefaultItemAnimator());
        adapter = new CartListViewAdapter(this);
        cartList.setAdapter(adapter);
    }

    @Override
    public void onCartItemRemove(View view, int cartId) {
        presenter.onRemoveCartItem(cartId);
    }

    @Override
    public Loader<ShoppingCartPresenter> onCreateLoader(int i, Bundle bundle) {
        return new CartListLoader(getContentActivity(), presenter);
    }

    @Override
    public void onLoadFinished(Loader<ShoppingCartPresenter> loader, ShoppingCartPresenter presenter) {
        this.presenter = presenter;
        this.presenter.updateCartList();
    }

    @Override
    public void onLoaderReset(Loader<ShoppingCartPresenter> loader) {
        this.presenter.setCartItems(new CopyOnWriteArrayList<CartItem>());
    }

    @Override
    public void updateCartList(@NonNull List<CartItem> cartItems) {
        progress.setVisibility(GONE);
        initialize();
        cartList.setVisibility(VISIBLE);
        if (!cartItems.isEmpty()) {
            totalCartPrice.setVisibility(VISIBLE);
            presenter.showTotalCartPrice();
            adapter.setCartItems(cartItems);
            adapter.notifyDataSetChanged();
        } else {
            emptyCartMessage.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onProductRemoveFromCart(boolean running, int cartId) {
        showProgress(running);
        adapter.notifyDataSetChanged();
    }

    public void showMessage(boolean success) {
        String message;
        if (success) {
            message = getResources().getString(R.string.cart_remove_message_success);
        } else {
            message = getResources().getString(R.string.message_fail);
        }
        showMessage(message);
    }

    @Override
    public void updateTotalCartPrice(double totalPrice) {
        String formattedPrice = String.format(Locale.getDefault(), "%s : %s%.2f", getResources().getString(R.string.total_price), POUNDS, totalPrice);
        totalCartPrice.setText(formattedPrice);
    }

    /**
     * Get the {@link Toolbar} title
     *
     * @return {@link String} instance
     */
    @NonNull
    @Override
    protected String getToolbarTitle() {
        return getResources().getString(R.string.shopping_cart);
    }

    /**
     * for testing purpose, returns the adapter
     *
     * @return {@link CartListViewAdapter} instance
     */
    public CartListViewAdapter getAdapter() {
        return adapter;
    }

    /**
     * for testing purpose, returns the {@link RecyclerView} which displays product list
     *
     * @return {@link RecyclerView} instance
     */
    public RecyclerView getCartList() {
        return cartList;
    }

    /**
     * for testing purpose, returns the {@link ShoppingCartPresenter} which displays product list
     *
     * @return {@link ShoppingCartPresenter} instance
     */
    public ShoppingCartPresenter getPresenter() {
        return presenter;
    }
}
