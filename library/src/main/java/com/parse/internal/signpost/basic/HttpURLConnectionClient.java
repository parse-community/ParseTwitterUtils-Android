package com.parse.internal.signpost.basic;

import java.net.HttpURLConnection;
import java.net.URL;

public interface HttpURLConnectionClient {
    HttpURLConnection open(URL url) throws Exception;
}
