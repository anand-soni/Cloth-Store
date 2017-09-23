package com.theclothingstore.mystore.helper;

/**
 * @author Anand Soni
 */

public class Constants {

    public static class StringKeys {
        public static final String API_REQUEST = "apiRequest";
        public static final String PRODUCT_ID = "productId";
        public static final String CART_ID = "cartId";
    }

    public static class LoaderId {
        public static final int LOADER_ID_FETCH_DATA = 1001;
        public static final int LOADER_ID_ADD_PRODUCT_TO_CART = 1002;
        public static final int LOADER_ID_REMOVE_PRODUCT_FROM_CART = 1003;
    }

    public static class APIRequest {
        public static final int FETCH_DATA = 9001;
        public static final int ADD_PRODUCT_TO_CART = 9002;
        public static final int REMOVE_PRODUCT_FROM_CART = 9003;
    }

    public static class UnicodeConstants {
        public static final String POUNDS = "\u00a3";
    }

}
