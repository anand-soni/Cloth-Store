package com.theclothingstore.mystore.fragments.productcatalogue;

import android.view.View;
import android.view.ViewGroup;

import com.theclothingstore.mystore.model.Product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;

/**
 * @author Anand Soni
 */
@RunWith(MockitoJUnitRunner.class)
public class ProductListViewAdapterTest {

    @org.junit.Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    ProductListViewHolder.OnAddCartListener listener;
    @Mock
    View view;

    private ProductListViewAdapter adapter;

    @Before
    public void setUp() {
        adapter = new ProductListViewAdapter(listener);
    }

    @Test
    public void testNewInstance() {
        assertNotNull(adapter);
    }

    @Test
    public void getItemCount() {
        adapter.setProductList(mockProductList());
        assertEquals(adapter.getItemCount(), mockProductList().size());
    }

    @Test
    public void onCreateViewHolder() {
        TestProductListViewAdapter adapter = new TestProductListViewAdapter(listener);
        adapter.setMockView(view);
        ProductListViewHolder holder = adapter.onCreateViewHolder(any(ViewGroup.class), anyInt());
        assertEquals(holder.itemView, view);
    }

    private List<Product> mockProductList() {
        List<Product> products = new ArrayList<>();
        products.add(mock(Product.class));
        products.add(mock(Product.class));
        products.add(mock(Product.class));
        return products;
    }

    class TestProductListViewAdapter extends ProductListViewAdapter {

        private View mockView;

        TestProductListViewAdapter(ProductListViewHolder.OnAddCartListener listener) {
            super(listener);
        }

        void setMockView(View mockView) {
            this.mockView = mockView;
        }

        @Override
        public View getLayout(ViewGroup parent) {
            return mockView;
        }
    }
}