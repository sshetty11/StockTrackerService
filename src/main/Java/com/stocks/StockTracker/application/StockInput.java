package com.stocks.StockTracker.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StockInput {
	static List<String> stockList ;
	private StockInput(){

	}
	public static void initialize(String dataInput) throws FileNotFoundException {
		if (stockList == null){
			File fileName = new File(dataInput);
			if (fileName.canRead()){
				Scanner file = new Scanner(fileName);
				//read the stock input from file
				while (file.hasNextLine()){
					String line = file.nextLine();
					stockList.add(line);
				}
			}
		}
	}
	


}
