package com.stocks.StockTracker.application;

import io.dropwizard.setup.Environment;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.stocks.StockTracker.model.StockConfig;
import io.dropwizard.Application;

public class App extends Application<StockConfig> {
    public static void main(String[] args ) throws Exception {
        new App().run(args);
    }

    @Override
    public void run(StockConfig config, Environment env) throws ClassNotFoundException, IOException, ParseException{
        StockInput.initialize(config.getDataInput());
        WebAPI.initialize(config.getEnvFile());

        final StockService stockService = new StockService();
        env.jersey().register(stockService);
    }
}
