package com.theclothingstore.mystore.controllers;

import android.app.FragmentManager;
import android.app.FragmentTransaction;

import com.theclothingstore.mystore.ContentActivity;
import com.theclothingstore.mystore.fragments.BaseFragment;
import com.theclothingstore.mystore.fragments.shopingcart.ShoppingCartFragment;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Anand Soni
 */
public class NavigationControllerTest {

    @org.junit.Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    ContentActivity mockActivity;
    @Mock
    FragmentManager fragmentManager;
    @Mock
    FragmentTransaction fragmentTransaction;
    @Mock

    private NavigationController navigationController;

    @Before
    public void setUp() throws Exception {
        navigationController = new NavigationController(mockActivity);
        when(mockActivity.getFragmentManager()).thenReturn(fragmentManager);
        when(fragmentManager.beginTransaction()).thenReturn(fragmentTransaction);
    }

    @Test
    public void openWelcomeScreen() throws Exception {
        navigationController.openProductCatalogueScreen();
        verifyTransactionDone();
    }

    @Test
    public void openShoppingCartScreen() throws Exception {
        navigationController.openShoppingCartScreen();
        verifyTransactionDone();
    }

    @Test
    public void getFragmentName() {
        Assert.assertEquals(navigationController.getFragmentName(ShoppingCartFragment.class),
                ShoppingCartFragment.class.getSimpleName());
    }

    private void verifyTransactionDone() {
        verify(fragmentTransaction).replace(eq(ContentActivity.CONTAINER_ID), any(BaseFragment.class), anyString());
        verify(fragmentTransaction).commit();
    }

}