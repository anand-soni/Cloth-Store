package com.theclothingstore.mystore.helper;

import java.io.IOException;

/**
 * @author Anand Soni
 */

public class NetworkConnectivityException extends IOException {

    @Override
    public String getMessage() {
        return "No connectivity exception";
    }
}
