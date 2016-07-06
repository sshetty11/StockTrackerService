package com.stocks.StockTracker;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


public class SecondTest {
	@Test
	public void SecondTestCase() throws InterruptedException{
		WebDriver driver = new FirefoxDriver();
		String url = "http://localhost:8000/index.html";
		String title = "Stock Tracker";
		String stockName = "AMZN";
		String btnValue = "Stock Value";
		//Launch the Online Store Website
		driver.get(url);
		WebElement weStockName = driver.findElement(By.id("input-0"));
		WebElement divStock = driver.findElement(By.id("divStock"));
		WebElement divNews = driver.findElement(By.id("listGroup"));
		WebElement stockValue = driver.findElement(By.id("stockValue"));



		// Verify the title
		String actualTitle = driver.getTitle();
		Assert.assertEquals(actualTitle, title);

		//Verify the url
		String actualUrl = driver.getCurrentUrl();
		Assert.assertEquals(actualUrl, url);

		//Verify if the stock value is displayed
		Assert.assertEquals(divStock.isDisplayed(), false);
		Assert.assertEquals(divNews.isDisplayed(), false);
		


			//Wait for 5 Sec
		Thread.sleep(5);
		driver.quit();
	}
}
