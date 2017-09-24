package com.theclothingstore.mystore.fragments.shopingcart;

import android.content.Context;
import android.test.LoaderTestCase;

import com.theclothingstore.mystore.data.DataHelper;
import com.theclothingstore.mystore.model.CartResponse;
import com.theclothingstore.mystore.model.Product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Anand Soni
 */
@RunWith(MockitoJUnitRunner.class)
public class CartListLoaderTest extends LoaderTestCase {

    @Mock
    DataHelper model;
    @Mock
    Context context;
    @Mock
    ShoppingCartView view;
    @Mock
    ArrayList list;
    @Mock
    Product product;
    @Mock
    CartResponse response;

    private ShoppingCartPresenter presenter;
    private CartListLoader loader;

    @Before
    public void setUp() {
        presenter = spy(new ShoppingCartPresenter(model, view));
        loader = spy(new CartListLoader(context, presenter));
    }

    @Test
    public void testNewInstance() {
        assertNotNull(loader);
    }

    @Test
    public void onStartLoading() {
        loader.onStartLoading();
        verify(loader).forceLoad();
    }

    @Test
    public void testLoader() throws IOException {
        when(response.getCartId()).thenReturn(1);
        when(presenter.getCartResponseItems()).thenReturn(mockCartResponseItems());
        when(model.getProduct(anyInt())).thenReturn(product);
        assertEquals(presenter.getCartItems().size(), 0);
        getLoaderResultSynchronously(loader);
        assertEquals(presenter.getCartItems().size(), 4);
        assertEquals(presenter.getCartItems().get(0).getProduct(), product);
        assertEquals(presenter.getCartItems().get(0).getCartId(), 1);
    }

    private ArrayList<CartResponse> mockCartResponseItems() {
        ArrayList<CartResponse> cartResponseList = new ArrayList<>();
        cartResponseList.add(response);
        cartResponseList.add(response);
        cartResponseList.add(response);
        cartResponseList.add(response);
        return cartResponseList;
    }
}