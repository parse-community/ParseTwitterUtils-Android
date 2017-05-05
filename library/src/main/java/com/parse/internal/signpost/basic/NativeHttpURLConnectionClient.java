package com.parse.internal.signpost.basic;

import java.net.HttpURLConnection;
import java.net.URL;

final class NativeHttpURLConnectionClient implements HttpURLConnectionClient {

    public HttpURLConnection open(URL url) throws Exception {
        return (HttpURLConnection) url.openConnection();
    }
}
