package com.theclothingstore.mystore.fragments.productcatalogue;

import android.content.Context;
import android.test.LoaderTestCase;

import com.theclothingstore.mystore.data.DataHelper;
import com.theclothingstore.mystore.model.Product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Anand Soni
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductListLoaderTest extends LoaderTestCase {

    @Mock
    DataHelper model;
    @Mock
    Context context;
    @Mock
    ProductCatalogueView view;
    @Mock
    ArrayList list;

    private ProductCataloguePresenter presenter;
    private ProductListLoader loader;

    @Before
    public void setUp() {
        presenter = spy(new ProductCataloguePresenter(view, model));
        loader = spy(new ProductListLoader(context, presenter));
    }

    @Test
    public void testNewInstance() {
        assertNotNull(loader);
    }

    @Test
    public void onStartLoadingWhenProductsAvailable() {
        when(list.isEmpty()).thenReturn(false);
        doReturn(list).when(presenter).getProductList();
        loader.onStartLoading();
        verify(loader).deliverResult(presenter);
    }

    @Test
    public void onStartLoadingWhenNoProducts() {
        when(list.isEmpty()).thenReturn(true);
        doReturn(list).when(presenter).getProductList();
        loader.onStartLoading();
        verify(loader).forceLoad();
    }

    @Test
    public void testLoader() throws IOException {
        when(model.getAllProducts()).thenReturn(mockProductList());
        assertEquals(presenter.getProductList().size(), 0);
        getLoaderResultSynchronously(loader);
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