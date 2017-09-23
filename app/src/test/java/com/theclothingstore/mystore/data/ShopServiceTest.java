package com.theclothingstore.mystore.data;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

/**
 * @author Anand Soni
 */
public class ShopServiceTest {

    @org.junit.Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private ShopService shopService;

    @Before
    public void setUp() throws Exception {
        shopService = new ShopService();
    }

    @Test
    public void getShopAPI() {
        Assert.assertNotNull(shopService.getShopAPI());
    }
}