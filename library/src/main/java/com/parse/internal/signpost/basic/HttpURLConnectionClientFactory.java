package com.parse.internal.signpost.basic;

public class HttpURLConnectionClientFactory {

    private HttpURLConnectionClientFactory() {
    }

    public static HttpURLConnectionClient create() {
        try {
            return new OkHttp3URLConnectionClient();
        } catch (Exception e) {
            try {
                return new OkHttpURLConnectionClient();
            } catch (Exception e1) {
                return new NativeHttpURLConnectionClient();
            }
        }
    }
}
