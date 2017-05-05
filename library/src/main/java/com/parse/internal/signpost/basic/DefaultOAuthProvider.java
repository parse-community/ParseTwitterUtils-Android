/*
 * Copyright (c) 2009 Matthias Kaeppler Licensed under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.parse.internal.signpost.basic;

import com.parse.internal.signpost.AbstractOAuthProvider;
import com.parse.internal.signpost.http.HttpRequest;
import com.parse.internal.signpost.http.HttpResponse;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * This default implementation uses {@link java.net.HttpURLConnection} type GET
 * requests to receive tokens from a service provider.
 * 
 * @author Matthias Kaeppler
 */
@SuppressWarnings("all")
public class DefaultOAuthProvider extends AbstractOAuthProvider {

    private static final long serialVersionUID = 1L;

    private transient HttpURLConnectionClient httpURLConnectionClient;

    public DefaultOAuthProvider(String requestTokenEndpointUrl, String accessTokenEndpointUrl,
            String authorizationWebsiteUrl) {
        super(requestTokenEndpointUrl, accessTokenEndpointUrl, authorizationWebsiteUrl);
        this.httpURLConnectionClient = HttpURLConnectionClientFactory.create();
    }

    public DefaultOAuthProvider(String requestTokenEndpointUrl, String accessTokenEndpointUrl,
            String authorizationWebsiteUrl, HttpURLConnectionClient httpURLConnectionClient) {
        super(requestTokenEndpointUrl, accessTokenEndpointUrl, authorizationWebsiteUrl);
        this.httpURLConnectionClient = httpURLConnectionClient;
    }

    public void setHttpURLConnectionClient(HttpURLConnectionClient httpURLConnectionClient) {
        this.httpURLConnectionClient = httpURLConnectionClient;
    }

    protected HttpRequest createRequest(String endpointUrl) throws Exception {
        HttpURLConnection connection = httpURLConnectionClient.open(new URL(endpointUrl));
        connection.setRequestMethod("POST");
        connection.setAllowUserInteraction(false);
        connection.setRequestProperty("Content-Length", "0");
        return new HttpURLConnectionRequestAdapter(connection);
    }

    protected HttpResponse sendRequest(HttpRequest request) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) request.unwrap();
        connection.connect();
        return new HttpURLConnectionResponseAdapter(connection);
    }

    @Override
    protected void closeConnection(HttpRequest request, HttpResponse response) {
        HttpURLConnection connection = (HttpURLConnection) request.unwrap();
        if (connection != null) {
            connection.disconnect();
        }
    }
}
