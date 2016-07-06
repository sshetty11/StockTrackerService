package com.stocks.StockTracker.application;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.IOException;

public class HttpWebRequestApi {
    static final HttpTransport HTTP_TRANSPORT = 	new NetHttpTransport();
    static final JsonFactory JSON_FACTORY = new JacksonFactory();
    static final HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(new HttpRequestInitializer() {
        public void initialize(HttpRequest request) throws IOException {
            request.setParser(new JsonObjectParser(JSON_FACTORY));
        }

    });

    public HttpResponse makeHttpRequest(String inputUrl, HttpHeaders headers)  throws IOException {
        GenericUrl url = new GenericUrl(inputUrl);
        //System.out.println("url:"+url);
        HttpRequest request = requestFactory.buildGetRequest(url);
        if (headers!=null){
        request.setHeaders(headers);
        }
        //System.out.println(request);
        return request.execute();
    }
}