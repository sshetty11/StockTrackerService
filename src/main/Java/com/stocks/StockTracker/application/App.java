package com.stocks.StockTracker.application;

import io.dropwizard.setup.Environment;


import io.dropwizard.Application;

/**
 * Hello world!
 *
 */
public class App extends Application<StockConfig>
{
	public static void main( String[] args ) throws Exception
	{
		new App().run(args);
	}
	@Override
	public void run(StockConfig config,Environment env) throws ClassNotFoundException{
		final StockService stockService = new StockService();
		env.jersey().register(stockService);
		//jdbi.registerArgumentFactory(new ListArgumentFactory());


	}
}
