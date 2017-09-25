package com.theclothingstore.mystore.fragments.shopingcart;

import com.theclothingstore.mystore.data.DataHelper;
import com.theclothingstore.mystore.model.CartItem;
import com.theclothingstore.mystore.model.CartResponse;
import com.theclothingstore.mystore.model.Product;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

import retrofit2.Callback;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
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
    CartResponse cartResponseOne;
    @Mock
    CartResponse cartResponseTwo;

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
        assertEquals(presenter.getDataHelper(), model);
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

    @Test
    public void onRemoveCartItem() throws IOException {
        presenter.onRemoveCartItem(1);
        verify(view).onProductRemoveFromCart(true, 1);
        verify(model).removeProductFromCart(eq(1), any(Callback.class));
    }

    @Test
    public void removeProductFromCart() {
        presenter.setCartItems(getCartItems());
        ArrayList<CartResponse> responses = getCartResponseItems();
        presenter.setCartResponses(responses);
        assertEquals(presenter.getCartItems().size(), 2);
        assertEquals(presenter.getCartResponseItems().size(), 2);
        presenter.removeProductFromCart(1);
        assertEquals(presenter.getCartItems().size(), 1);
        assertEquals(presenter.getCartResponseItems().size(), 1);
        verify(model).saveCartDataLocally(responses);
    }

    @Test
    public void getTotalPrice() {
        presenter.setCartItems(getCartItems());
        double price  = presenter.getTotalPrice();
        assertEquals(price, 198.0);

    }

    @Test
    public void updateCartList() {
        presenter.updateCartList();
        verify(view).updateCartList(ArgumentMatchers.<CartItem>anyList());
    }

    @Test
    public void showTotalPrice() {
        presenter.showTotalCartPrice();
        verify(view).updateTotalCartPrice(anyDouble());
    }

    @Test
    public void saveCartResponse() {
        ArrayList<CartResponse> cartResponses = getCartResponseItems();
        presenter.setCartResponses(cartResponses);
        presenter.saveCartResponse();
        verify(model).saveCartDataLocally(cartResponses);
    }


    private CopyOnWriteArrayList<CartItem> getCartItems() {
        CopyOnWriteArrayList<CartItem> cartItems = new CopyOnWriteArrayList<>();
        CartItem cartItem = new CartItem();
        cartItem.setCartId(1);
        cartItem.setProduct(mockProduct());
        CartItem cartItem1 = new CartItem();
        cartItem1.setProduct(mockProduct());
        cartItem1.setCartId(2);
        cartItems.add(cartItem);
        cartItems.add(cartItem1);
        return cartItems;
    }

    private ArrayList<CartResponse> getCartResponseItems() {
        ArrayList<CartResponse> responses = new ArrayList<>();
        responses.add(cartResponseOne);
        responses.add(cartResponseTwo);
        when(cartResponseOne.getCartId()).thenReturn(1);
        return responses;
    }

    private ArrayList<CartResponse> mockCartResponse() {
        ArrayList<CartResponse> cartResponses = new ArrayList<>();
        cartResponses.add(mock(CartResponse.class));
        cartResponses.add(mock(CartResponse.class));
        cartResponses.add(mock(CartResponse.class));
        return cartResponses;
    }

    private Product mockProduct() {
        Product product = new Product();
        product.setProductName("Almond Toe Court Shoes, Patent Black");
        product.setProductPrice(99);
        return product;
    }

}