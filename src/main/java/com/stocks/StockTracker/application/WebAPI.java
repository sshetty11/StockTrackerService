package com.stocks.StockTracker.application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.util.ArrayMap;
import com.stocks.StockTracker.model.News;
import com.stocks.StockTracker.model.Stock;

public class WebAPI {
    final String PREFIX_URL_NAME = "http://finance.yahoo.com/webservice/v1/symbols/";
    final String SUFFIX_URL_NAME = "/quote?format=json";
    final String NEWS_URL = "https://api.cognitive.microsoft.com/bing/v5.0/news/search?q=stock+";
    final String USER_AGENT = "Mozilla/5.0 (Linux; Android 6.0; MotoE2(4G-LTE) Build/MPI24.65-39) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.81 Mobile Safari/537.36";
    final String CONTENT_TYPE = "application/json";
    static String bingKey = null;

    public WebAPI() {
        
    }

    public static void initialize(String keyFile) throws FileNotFoundException, IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object keyObject = parser.parse(new FileReader(keyFile));
        org.json.simple.JSONObject jsonObject =  (org.json.simple.JSONObject) keyObject;
        bingKey = (String) jsonObject.get("bing-key");
    }

    // Get and parse the stock value
    public Stock getStock(String id) throws IOException {
        String url = PREFIX_URL_NAME + id + SUFFIX_URL_NAME;
        HttpHeaders headers = new HttpHeaders();
        headers.setUserAgent(USER_AGENT);
        HttpWebRequestApi web = new HttpWebRequestApi();
        //Send an http request and get a response
        HttpResponse response = web.makeHttpRequest(url, headers);
        return parseStockResponse(response);    
    }

    public Stock parseStockResponse(HttpResponse response) throws IOException {
        Map<Object,Map<Object,Map<Object,Object>>> map = new HashMap<Object,Map<Object,Map<Object,Object>>>();
        map = response.parseAs(map.getClass());

        ArrayList <Map<Object,Map<Object,ArrayMap>>> arr = new ArrayList <Map<Object,Map<Object,ArrayMap>>>();
        arr = (ArrayList<Map<Object, Map<Object, ArrayMap>>>) map.get("list").get("resources");
        ObjectMapper mapper = new ObjectMapper();

        //Read required value from the hashmap 
        Stock stock = mapper.readValue(new JSONObject(arr.get(0).get("resource").get("fields")).toString(), Stock.class);
        return stock;
    }

    //Get and parse the stock news
    public List<News> getNews(String id) throws FileNotFoundException, IOException, ParseException {
        String url_name_news = NEWS_URL + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(CONTENT_TYPE);
        headers.set("Ocp-Apim-Subscription-Key", bingKey);

        //Make an http request
        HttpWebRequestApi web = new HttpWebRequestApi();
        HttpResponse news_response = web.makeHttpRequest(url_name_news, headers);
        return parseNews(news_response);
    }

    public List<News> parseNews(HttpResponse news_response) throws IOException {
        Map<String, List<ArrayMap>> map = new HashMap<String, List<ArrayMap>>();
        map = news_response.parseAs(map.getClass());

        List<ArrayMap> newsVals =  map.get("value");
        List<News> result= new ArrayList<News>();
        ObjectMapper mapper = new ObjectMapper();

        //Read value from array map using mapper and add to the list of news
        for (ArrayMap val: newsVals) {
            News n = mapper.readValue(new JSONObject(val).toString(), News.class);
            if (val.get("image") != null) {
                Map<Object,Map<Object,Object>> imgMap = new HashMap <Object,Map<Object,Object>>();
                imgMap = (Map<Object, Map<Object, Object>>) val.get("image");
                n.setContentUrl(imgMap.get("thumbnail").get("contentUrl").toString());
            }
            result.add(n);
        }
        return result;
    }
}
