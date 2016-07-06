package com.stocks.StockTracker.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
//import org.skife.jdbi.v2.DBI;
import org.json.simple.parser.ParseException;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.json.Json;
import com.google.api.client.json.JsonParser;
import com.google.api.client.util.ArrayMap;


@Path("/stock")
public class StockService {
	//private DBI jdbi = null;
	public StockService(){
		
	}
	
	@GET
	@Timed
	@Path("/autocomplete/{txtValue}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getAutoComplete(@PathParam("txtValue") String text, @QueryParam("callback") String callback ) throws FileNotFoundException{
		Test test = new Test();
		File fileName = new File("Companies.txt");
		List<String> autoValues = new ArrayList<String>();
		if (fileName.canRead()){
		Scanner file = new Scanner(fileName);
		//read the company name from file
		while (file.hasNextLine()){
			String line = file.nextLine();
			if(line.toLowerCase().contains(text.toLowerCase())){
				autoValues.add(line);
			}
		}
		
		}else{
			autoValues.add("Cannot open file");
		}
		test.setAuto(autoValues);
		if (callback != null) {
            JSONPObject obj = new JSONPObject(callback, autoValues);
            return obj;
        }
		return autoValues;
		
	}


	
	@GET
	@Timed
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Object getStocks(@PathParam("id") String id,@QueryParam("callback") String callback) throws IOException, JSONException, ParseException{
		// get stock value from yahoo api
		Stock stock = getStockValue(id);
		List<News> result = new ArrayList<News>();
		// get data from news api
		result = getStockNews(id);
	    StockResponse stockResponse  = new StockResponse();
	    stockResponse.setNews(result);
	    stockResponse.setStock(stock);
	    //on callback from front end then return as json object
	    if (callback != null) {
            
            JSONPObject obj = new JSONPObject(callback, stockResponse);
            return obj;
           
        }
	    return stockResponse;
		//return stock;
		
	}
	
	public Stock getStockValue(String id) throws IOException{
		String url_name="http://finance.yahoo.com/webservice/v1/symbols/"+ id + "/quote?format=json";
		HttpHeaders headers = new HttpHeaders();
		HttpWebRequestApi web = new HttpWebRequestApi();
		//Send an http request and get a response
		HttpResponse response = web.makeHttpRequest(url_name,null);
		//map the response to hashmap
		Map<Object,Map<Object,Map<Object,Object>>> map = new HashMap<Object,Map<Object,Map<Object,Object>>>();
		map = response.parseAs(map.getClass());
		ArrayList <Map<Object,Map<Object,ArrayMap>>> arr = new ArrayList <Map<Object,Map<Object,ArrayMap>>>();
		arr = (ArrayList<Map<Object, Map<Object, ArrayMap>>>) map.get("list").get("resources");
		ObjectMapper mapper = new ObjectMapper();
		//Read required value from the hashmap 
		Stock stock = mapper.readValue(new JSONObject(arr.get(0).get("resource").get("fields")).toString(), Stock.class);
		return stock;
	}
	
	public List<News> getStockNews(String id) throws IOException, ParseException{
		String url_name_news = "https://bingapis.azure-api.net/api/v5/news/search?q=stock+"+id;
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType("application/json");
		JSONParser parser = new JSONParser();
		Object obj1 = parser.parse(new FileReader("envs.json"));
		org.json.simple.JSONObject jsonObject =  (org.json.simple.JSONObject) obj1;
		String key = (String) jsonObject.get("bing-key");
		//set the header value
		headers.set("Ocp-Apim-Subscription-Key", key);
		HttpWebRequestApi web = new HttpWebRequestApi();
		//Make an http request
		HttpResponse news_response = web.makeHttpRequest(url_name_news,headers);
		//map the response to a hashmap
		Map<String, List<ArrayMap>> c = new HashMap<String, List<ArrayMap>>();
		c = news_response.parseAs(c.getClass());
		List<ArrayMap> newsVals =  c.get("value");
		List<News> result= new ArrayList<News>();
		ObjectMapper mapper = new ObjectMapper();
		//Read value from array map using mapper and add to the list of news
		for (ArrayMap val: newsVals) {
			News n = mapper.readValue(new JSONObject(val).toString(), News.class);
	    	if (val.get("image")!=null){
		    	Map<Object,Map<Object,Object>> imgMap = new HashMap <Object,Map<Object,Object>>();
		    	imgMap = (Map<Object, Map<Object, Object>>) val.get("image");
		    	System.out.println(imgMap.get("thumbnail"));
		    	System.out.println(imgMap.get("thumbnail").get("contentUrl"));
		    	n.setContentUrl(imgMap.get("thumbnail").get("contentUrl").toString());
		    	}
	    	result.add(n);
	    }
	    return result;

	 }
	

	
}
