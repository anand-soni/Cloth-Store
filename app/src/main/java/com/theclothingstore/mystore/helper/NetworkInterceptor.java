package com.theclothingstore.mystore.helper;

import android.content.Context;
import android.support.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Anand Soni
 */

public class NetworkInterceptor implements Interceptor {

    private Context context;

    public NetworkInterceptor(@NonNull Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        if (!NetworkUtil.isNetworkAvailable(context)) {
            throw new NetworkConnectivityException();
        }

        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }
}
