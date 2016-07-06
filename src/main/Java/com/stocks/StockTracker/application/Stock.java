package com.stocks.StockTracker.application;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Stock {
	private String name;
	private String price;
	private String symbol;
	public Stock(){
		
	}
	public Stock(String name, String price, String symbol){
		this.name = name;
		this.price = price;
		this.symbol = symbol;
	}
	@JsonProperty
	public String getName() {
		return name;
	}
	@JsonProperty
	public void setName(String name) {
		this.name = name;
	}
	@JsonProperty
	public String getPrice() {
		return price;
	}
	@JsonProperty
	public void setPrice(String price) {
		this.price = price;
	}
	@JsonProperty
	public String getSymbol() {
		return symbol;
	}
	@JsonProperty
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stock other = (Stock) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Stock [name=" + name + ", price=" + price + ", symbol=" + symbol + "]";
	}
	
}
