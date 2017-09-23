package com.theclothingstore.mystore.fragments.productcatalogue;

import android.content.Context;
import android.test.LoaderTestCase;

import com.theclothingstore.mystore.model.Product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Anand Soni
 */
@RunWith(MockitoJUnitRunner.class)
public class CataloguePresenterLoaderTest extends LoaderTestCase {

    @Mock
    ProductCatalogueModel model;
    @Mock
    Context context;
    @Mock
    ProductCatalogueView view;

    private ProductCataloguePresenter presenter;
    private CataloguePresenterLoader presenterLoader;

    @Before
    public void setUp() {
        presenter = new ProductCataloguePresenter(view, model);
        presenterLoader = new CataloguePresenterLoader(context, presenter);
    }

    @Test
    public void testLoader() throws IOException {
        when(model.getAllProducts()).thenReturn(mockProductList());
        assertEquals(presenter.getProductList().size(), 0);
        getLoaderResultSynchronously(presenterLoader);
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