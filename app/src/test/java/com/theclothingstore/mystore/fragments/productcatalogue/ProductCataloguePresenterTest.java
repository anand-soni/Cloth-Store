package com.theclothingstore.mystore.fragments.productcatalogue;

import com.theclothingstore.mystore.data.DataHelper;
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
import java.util.List;

import retrofit2.Callback;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Anand Soni
 */
public class ProductCataloguePresenterTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Mock
    ProductCatalogueView view;
    @Mock
    DataHelper model;
    @Mock
    CartResponse cartResponse;

    private ProductCataloguePresenter presenter;

    @Before
    public void setUp() {
        when(model.fetchCartResponse()).thenReturn(mockCartResponse());
        presenter = new ProductCataloguePresenter(view, model);
    }

    @Test
    public void newInstance() {
        assertNotNull(presenter);
        assertEquals(presenter.getCartResponses().size(), mockCartResponse().size());
    }

    @Test
    public void updateProductList() {
        presenter.updateProductList();
        verify(view).updateProductList(ArgumentMatchers.<Product>anyList());
    }

    @Test
    public void setProductList() {
        presenter.setProductList(mockProductList());
        assertEquals(presenter.getProductList().size(), 3);
    }

    @Test
    public void getProductList() {
        assertNotNull(presenter.getProductList());
    }

    @Test
    public void getCartResponses() {
        assertNotNull(presenter.getCartResponses());
    }

    @Test
    public void getProductModel() {
        assertEquals(presenter.getProductModel(), model);
    }

    @Test
    public void updateView() {
        presenter.updateProductList();
        verify(view).updateProductList(ArgumentMatchers.<Product>anyList());
    }

    @Test
    public void addProductToCart() throws IOException {
        presenter.addProductToCart(1);
        verify(view).onProductAddToCart(true, 1);
        verify(model).addProductToCart(eq(1), any(Callback.class));
    }

    @Test
    public void saveCartResponse() {
        presenter.saveCartResponse();
        verify(model).saveCartDataLocally(ArgumentMatchers.<CartResponse>anyList());
    }

    @Test
    public void setCartResponseItems() {
        presenter.setCartResponseItems();
        verify(model, atLeastOnce()).fetchCartResponse();
        assertEquals(presenter.getCartResponses().size(), mockCartResponse().size());
    }

    private ArrayList<CartResponse> mockCartResponse() {
        ArrayList<CartResponse> cartResponses = new ArrayList<>();
        cartResponses.add(mock(CartResponse.class));
        cartResponses.add(mock(CartResponse.class));
        cartResponses.add(mock(CartResponse.class));
        return cartResponses;
    }

    private List<Product> mockProductList() {
        List<Product> products = new ArrayList<>();
        products.add(mock(Product.class));
        products.add(mock(Product.class));
        products.add(mock(Product.class));
        return products;
    }
}