package com.theclothingstore.mystore.fragments.productcatalogue;

import com.theclothingstore.mystore.model.Product;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Anand Soni
 */
public class ProductCataloguePresenterTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @Mock
    ProductCatalogueView view;
    @Mock
    ProductCatalogueModel model;

    private ProductCataloguePresenter presenter;

    @Before
    public void setUp() {
        presenter = new ProductCataloguePresenter(view, model);
    }

    @Test
    public void updateView() {
        presenter.updateView();
        verify(view).updateView(ArgumentMatchers.<Product>anyList());
    }

    @Test
    public void setProductList() {
        presenter.setProductList(mockProductList());
        assertEquals(presenter.getProductList().size(), 3);
    }

    private List<Product> mockProductList() {
        List<Product> products = new ArrayList<>();
        products.add(mock(Product.class));
        products.add(mock(Product.class));
        products.add(mock(Product.class));
        return products;
    }
}