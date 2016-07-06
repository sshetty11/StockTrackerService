package com.stocks.StockTracker.application;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonValue {
	private News[] value;
	public JsonValue(){
		
	}
	public JsonValue(News[] value){
		this.value = value;
	}
	@JsonProperty
	public News[] getValue() {
		return value;
	}
	@JsonProperty
	public void setValue(News[] value) {
		this.value = value;
	}
	
}
