package com.parse.internal.signpost.basic;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Adapter class to provide HttpURLConnection, using android HttpURLConnection or OkHttp
 */
public final class NativeHttpURLConnectionClient implements HttpURLConnectionClient {

    public HttpURLConnection open(URL url) throws Exception {
        return (HttpURLConnection) url.openConnection();
    }
}
