package com.stocks.StockTracker.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.apache.commons.collections4.trie.PatriciaTrie;

public class StockInput {
    static PatriciaTrie<String> stockList;
    
    public StockInput(){
    }
    
    //Initialize the tree with values from stock file
    public static void initialize(String dataInput) throws FileNotFoundException {
        if (stockList == null) {
            stockList = new PatriciaTrie<String>();
            File fileName = new File(dataInput);
            if (fileName.canRead()) {
                Scanner file = new Scanner(fileName);
                //read the stock input from file
                while (file.hasNextLine()) {
                    String line = file.nextLine();
                    String[] syms = line.split("\",\"");
                    // Prefix Company description followed by Company stock symbol
                    String search = syms[1].toLowerCase() + "," + syms[0].substring(1, syms[0].length());
                    stockList.put(search, search);
                }
                file.close();
            }
        }
    }
}
