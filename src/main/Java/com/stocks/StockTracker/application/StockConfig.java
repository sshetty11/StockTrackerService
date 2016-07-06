package com.stocks.StockTracker.application;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class StockConfig extends Configuration{
	 @NotEmpty
	    private String version;
	 
	    @JsonProperty
	    public String getVersion() {
	        return version;
	    }
	 
	    @JsonProperty
	    public void setVersion(String version) {
	        this.version = version;
	    }
}
