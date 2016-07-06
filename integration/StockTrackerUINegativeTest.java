package com.stocks.StockTracker;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirstTest {
	@Test
	public void FirstTestCase() throws InterruptedException{
		WebDriver driver = new FirefoxDriver();
		String url = "http://localhost:8000/index.html";
		String title = "Stock Tracker";
		String stockName = "AMZN";
		String btnValue = "Stock Value";
		//Launch the Online Store Website
		driver.get(url);
		WebElement weStockName = driver.findElement(By.id("input-0"));
		weStockName.sendKeys(stockName);
		WebElement body = 		driver.findElement(By.tagName("body"));
		body.click();
		WebElement btnSubmit = driver.findElement(By.id("btnSubmit"));
		btnSubmit.click();
		WebElement divStock = driver.findElement(By.id("divStock"));
		WebElement divNews = driver.findElement(By.id("listGroup"));
		WebElement stockValue = driver.findElement(By.id("stockValue"));

		
		

		// Verify the title
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, title);
		//Verify the url
		String actualUrl = driver.getCurrentUrl();
		Assert.assertEquals(actualUrl, url);

//		//Verify the text value
		Assert.assertEquals(weStockName.getAttribute("value"), stockName);
//		//Verify the button text
		Assert.assertEquals(btnSubmit.getAttribute("value"), btnValue);

//		//Verify if the stock value is displayed
		Assert.assertEquals(divStock.isDisplayed(), true);
		Assert.assertEquals(divNews.isDisplayed(), true);

//		
//		//Verify if stock value is numeric
		Assert.assertEquals(isNumeric(stockValue.getText()), true);



			//Wait for 5 Sec
		Thread.sleep(5);

		// Close the driver
		driver.quit();
		driver = null;
	}
	public static boolean isNumeric(String str){
		return java.util.regex.Pattern.matches("^([+-]?\\d*\\.?\\d*)$", str);

	}

}
