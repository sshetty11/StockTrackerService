package com.stocks.StockTracker.application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.json.JSONException;
import org.json.simple.parser.ParseException;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.stocks.StockTracker.model.News;
import com.stocks.StockTracker.model.Stock;
import com.stocks.StockTracker.model.StockResponse;

@Path("/stock")
public class StockService {
    private WebAPI webAPI = new WebAPI();

    public StockService(){

    }

    //method for mocking webapi for testing
    public void setWebAPI(WebAPI webAPI) {
        this.webAPI = webAPI;
    }

    @GET
    @Timed
    @Path("/autocomplete/{txtValue}")
    @Produces(MediaType.APPLICATION_JSON)
    public Object getAutoComplete(@PathParam("txtValue") String text, @QueryParam("callback") String callback ) 
    		throws FileNotFoundException{
        List<String> result = new ArrayList<String>();
        SortedMap<String, String> map = StockInput.stockList.prefixMap(text.toLowerCase());

        for (String e: map.keySet()) {
            result.add(e);
        }
        if (callback != null) {
            JSONPObject jsonResult = new JSONPObject(callback, result);
            return jsonResult;
        }
        return result;
    }

    @GET
    @Timed
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Object getStocks(@PathParam("id") String id,@QueryParam("callback") String callback) 
            throws IOException, JSONException, ParseException {
        StockResponse stockResponse  = new StockResponse();

        // get stock value
        Stock stock = webAPI.getStock(id);
        stockResponse.setStock(stock);

        // get data from news api
        List<News> result = new ArrayList<News>();
        result = webAPI.getNews(id);
        stockResponse.setNews(result);

        //on callback from front end then return as json object
        if (callback != null) {
            JSONPObject obj = new JSONPObject(callback, stockResponse);
            return obj;
        }
        return stockResponse;
    }
}
