package com.parse.internal.signpost.basic;

/**
 * Created by vjames19 on 2/8/16.
 */
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
