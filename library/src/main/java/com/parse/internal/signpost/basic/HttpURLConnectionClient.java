package com.parse.internal.signpost.basic;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by vjames19 on 2/8/16.
 */
public interface HttpURLConnectionClient {
    HttpURLConnection open(URL url) throws Exception;
}
