package com.stocks.StockTracker.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class News {
    private String url;
    private String name;
    private String description;
    private String contentUrl;
    
    public News(){
        
    }
    
    public News(String url, String name, String description){
        this.url = url;
        this.name = name;
        this.description = description;
    }
    
    @JsonProperty
    public String getUrl() {
        return url;
    }
    
    @JsonProperty
    public void setUrl(String url) {
        this.url = url;
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
    public String getDescription() {
        return description;
    }
    
    @JsonProperty
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getContentUrl() {
        return contentUrl;
    }
    
    public void setContentUrl(String contentUrl) {
        this.contentUrl = contentUrl;
    }
    
    @Override
    public String toString() {
        return "News [url=" + url + ", name=" + name + ", description=" + description + ", contentUrl=" + contentUrl
                + "]";
    }
}
