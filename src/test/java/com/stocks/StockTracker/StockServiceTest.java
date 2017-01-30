package com.stocks.StockTracker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.stocks.StockTracker.application.StockInput;
import com.stocks.StockTracker.application.StockService;
import com.stocks.StockTracker.application.WebAPI;
import com.stocks.StockTracker.model.News;
import com.stocks.StockTracker.model.Stock;
import com.stocks.StockTracker.model.StockResponse;

import mockit.Expectations;
import mockit.Mocked;

public class StockServiceTest {
	@Mocked
	WebAPI webAPI;
	StockService stockService = new StockService();
	final String CALL_BACK = "JSON_CALLBACK";
	
	@Test
	public void testgetAutoCompletWithCallback() throws FileNotFoundException {
		List<String> expectedResult = new ArrayList<String>();
		expectedResult.add("amayan pharmaceuticals, inc.,AMAY");
		expectedResult.add("amazon, inc.,AMZN");
		JSONPObject jsonResult = new JSONPObject(CALL_BACK, expectedResult);
		
		StockInput.initialize("src/test/resources/TestCompanyList.csv");
		JSONPObject actualResult = (JSONPObject) stockService.getAutoComplete("ama", CALL_BACK);
		
		Assert.assertEquals(jsonResult.getValue().toString(), actualResult.getValue().toString());
	}
	
	@Test
	public void testgetAutoCompleteWithoutCallback() throws FileNotFoundException {
		List<String> expectedResult = new ArrayList<String>();
		expectedResult.add("amayan pharmaceuticals, inc.,AMAY");
		expectedResult.add("amazon, inc.,AMZN");
		
		StockInput.initialize("src/test/resources/TestCompanyList.csv");
		List<String> actualResult = (List<String>) stockService.getAutoComplete("ama", null);
		
		Assert.assertEquals(expectedResult, actualResult);
	}
	
	@Test
	public void testgetStocksWithCallback() throws IOException, JSONException, ParseException {
		StockResponse stockResponse = new StockResponse();
		final Stock stock = new Stock("Amazon", "800", "AMZN");
		final List<News> newsList = new ArrayList<News>();
		String url = "http://perspectivabetica.com/2017/01/30/the-amazon-com-inc-amzn-stock-rating-reaffirmed-by-robert-w/";
		String name = "The Amazon.com, Inc. (AMZN) Stock Rating Reaffirmed";
		String description = "Surprisingly, even as the company's Azure cloud platform has grown";
		News news = new News(url, name, description);
		newsList.add(news);
		
		url = "http://www.profitconfidential.com/stock/amzn-stock-amazon-com-inc-ceo-jeff-bezos-new-warren-buffett/";
		name = "AMZN Stock: Amazon.com, Inc. CEO Jeff Bezos Is the New Warren Buffett";
		description = "Amazon.com, Inc. (NASDAQ:AMZN) had its most successful year ever in 2016";
		news = new News(url, name, description);
		newsList.add(news);
		stockResponse.setStock(stock);
		stockResponse.setNews(newsList);
		JSONPObject expectedResponse = new JSONPObject(CALL_BACK, stockResponse);;
		stockService.setWebAPI(webAPI);

		new Expectations() {{
			webAPI.getStock(anyString); result = stock;
			webAPI.getNews(anyString); result = newsList;
		}};
		
		JSONPObject actualResponse = (JSONPObject) stockService.getStocks("AMZN", CALL_BACK);
		Assert.assertEquals(expectedResponse.getValue().toString(), actualResponse.getValue().toString());
	}
	
	@Test
	public void testgetStocksWithoutCallback() throws IOException, JSONException, ParseException {
		StockResponse stockResponse = new StockResponse();
		final Stock stock = new Stock("Amazon", "800", "AMZN");
		final List<News> newsList = new ArrayList<News>();
		String url = "http://perspectivabetica.com/2017/01/30/the-amazon-com-inc-amzn-stock-rating-reaffirmed-by-robert-w/";
		String name = "The Amazon.com, Inc. (AMZN) Stock Rating Reaffirmed";
		String description = "Surprisingly, even as the company's Azure cloud platform has grown";
		News news = new News(url, name, description);
		newsList.add(news);
		
		url = "http://www.profitconfidential.com/stock/amzn-stock-amazon-com-inc-ceo-jeff-bezos-new-warren-buffett/";
		name = "AMZN Stock: Amazon.com, Inc. CEO Jeff Bezos Is the New Warren Buffett";
		description = "Amazon.com, Inc. (NASDAQ:AMZN) had its most successful year ever in 2016";
		news = new News(url, name, description);
		newsList.add(news);
		stockResponse.setStock(stock);
		stockResponse.setNews(newsList);
		stockService.setWebAPI(webAPI);

		new Expectations() {{
			webAPI.getStock(anyString); result = stock;
			webAPI.getNews(anyString); result = newsList;
		}};
		
		StockResponse actualResponse =  (StockResponse) stockService.getStocks("AMZN", null);
		Assert.assertEquals(stockResponse, actualResponse);
	}
}
