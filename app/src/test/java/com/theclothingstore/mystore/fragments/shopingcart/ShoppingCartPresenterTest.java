package com.theclothingstore.mystore.fragments.shopingcart;

import com.theclothingstore.mystore.data.DataHelper;
import com.theclothingstore.mystore.model.CartItem;
import com.theclothingstore.mystore.model.CartResponse;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Anand Soni
 */
public class ShoppingCartPresenterTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Mock
    ShoppingCartView view;
    @Mock
    DataHelper model;
    @Mock
    CartResponse cartResponse;

    private ShoppingCartPresenter presenter;

    @Before
    public void setUp() {
        when(model.fetchCartResponse()).thenReturn(mockCartResponse());
        presenter = new ShoppingCartPresenter(model, view);
    }

    @Test
    public void newInstance() {
        assertNotNull(presenter);
        assertEquals(presenter.getCartResponseItems().size(), mockCartResponse().size());
    }

    @Test
    public void getProductList() {
        assertNotNull(presenter.getCartResponseItems());
    }

    @Test
    public void getCartResponses() {
        assertNotNull(presenter.getCartItems());
    }

    @Test
    public void getProductModel() {
        assertEquals(presenter.getProductModel(), model);
    }

    @Test
    public void addCartItem() {
        CopyOnWriteArrayList<CartItem> cartItems = new CopyOnWriteArrayList<>();
        presenter.setCartItems(cartItems);
        assertEquals(presenter.getCartItems().size(), 0);
        presenter.addCartItems(new CartItem());
        presenter.addCartItems(new CartItem());
        assertEquals(presenter.getCartItems().size(), 2);
    }

    private ArrayList<CartResponse> mockCartResponse() {
        ArrayList<CartResponse> cartResponses = new ArrayList<>();
        cartResponses.add(mock(CartResponse.class));
        cartResponses.add(mock(CartResponse.class));
        cartResponses.add(mock(CartResponse.class));
        return cartResponses;
    }

}