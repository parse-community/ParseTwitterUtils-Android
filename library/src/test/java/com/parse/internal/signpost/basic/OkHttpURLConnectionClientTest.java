package com.parse.internal.signpost.basic;

import org.junit.Rule;
import org.junit.Test;

import java.net.HttpURLConnection;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static junit.framework.Assert.assertEquals;

public class OkHttpURLConnectionClientTest {

    @Rule public MockWebServer server = new MockWebServer();


    @Test
    public void testConstructorShouldNotThrowException() throws Exception {
        new OkHttpURLConnectionClient();
    }

    @Test
    public void testOpen() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("Ok"));

        OkHttpURLConnectionClient client = new OkHttpURLConnectionClient();
        HttpURLConnection connection = client.open(server.url("/").url());

        assertEquals(200, connection.getResponseCode());
    }
}
