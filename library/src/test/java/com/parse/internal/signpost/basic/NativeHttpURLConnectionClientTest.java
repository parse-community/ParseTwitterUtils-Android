package com.parse.internal.signpost.basic;

import org.junit.Rule;
import org.junit.Test;

import java.net.HttpURLConnection;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static junit.framework.Assert.assertEquals;

/**
 * Created by vjames19 on 2/9/16.
 */
public class NativeHttpURLConnectionClientTest {

    @Rule public MockWebServer server = new MockWebServer();

    @Test
    public void testOpen() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("Ok"));

        NativeHttpURLConnectionClient client = new NativeHttpURLConnectionClient();
        HttpURLConnection connection = client.open(server.url("/").url());

        assertEquals(200, connection.getResponseCode());
    }
}
