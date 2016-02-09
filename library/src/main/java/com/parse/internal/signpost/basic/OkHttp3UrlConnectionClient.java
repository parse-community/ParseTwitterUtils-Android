package com.parse.internal.signpost.basic;

import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by vjames19 on 2/8/16.
 */
public class OkHttp3UrlConnectionClient implements HttpURLConnectionClient {

    private final Object okUrlFactory;
    private final Method okUrlFactoryOpen;

    public OkHttp3UrlConnectionClient() throws Exception {
        Class okHttpClientClass = Class.forName("okhttp3.OkHttpClient");
        Object okHttpClient = okHttpClientClass.getConstructor().newInstance();
        Class okUrlFactoryClass = Class.forName("okhttp3.OkUrlFactory");
        okUrlFactory = okUrlFactoryClass.getConstructor(okHttpClientClass).newInstance(okHttpClient);
        okUrlFactoryOpen = okUrlFactoryClass.getMethod("open", URL.class);
    }

    @Override
    public HttpURLConnection open(URL url) throws Exception {
        return (HttpURLConnection) okUrlFactoryOpen.invoke(okUrlFactory, url);
    }
}
