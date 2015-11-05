package com.parse.internal.signpost.basic;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Adapter class to provide HttpURLConnection, using android HttpURLConnection or OkHttp
 */
public final class HttpURLConnectionClient {
    private final boolean isUsingOkHttp;
    private final Object okUrlFactory;
    private final Method okUrlFactoryOpen;

    private HttpURLConnectionClient(boolean isUsingOkHttp, Object okUrlFactory, Method okUrlFactoryOpen) {
        this.isUsingOkHttp = isUsingOkHttp;
        this.okUrlFactory = okUrlFactory;
        this.okUrlFactoryOpen = okUrlFactoryOpen;
    }

    public static HttpURLConnectionClient create() {
        try {
            final Class okHttpClientClass = Class.forName("com.squareup.okhttp.OkHttpClient");
            final Object okHttpClient = okHttpClientClass.getConstructor().newInstance();
            final Class okUrlFactoryClass = Class.forName("com.squareup.okhttp.OkUrlFactory");
            final Object okUrlFactory = okUrlFactoryClass.getConstructor(okHttpClientClass).newInstance(okHttpClient);
            final Method okUrlFactoryOpen = okUrlFactoryClass.getMethod("open", URL.class);
            return new HttpURLConnectionClient(true, okUrlFactory, okUrlFactoryOpen);
        } catch (Exception e) {
            return new HttpURLConnectionClient(true, null, null);
        }
    }

    public HttpURLConnection open(URL url) throws Exception {
        if (isUsingOkHttp) {
            return (HttpURLConnection) okUrlFactoryOpen.invoke(okUrlFactory, url);
        } else {
            return (HttpURLConnection) url.openConnection();
        }
    }
}
