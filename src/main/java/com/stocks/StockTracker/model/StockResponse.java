package com.stocks.StockTracker.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StockResponse {
    private Stock stock;
    private List<News> news;
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((news == null) ? 0 : news.hashCode());
        result = prime * result + ((stock == null) ? 0 : stock.hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "StockResponse [stock=" + stock + ", news=" + news + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StockResponse other = (StockResponse) obj;
        if (news == null) {
            if (other.news != null)
                return false;
        } else if (!news.equals(other.news))
            return false;
        if (stock == null) {
            if (other.stock != null)
                return false;
        } else if (!stock.equals(other.stock))
            return false;
        return true;
    }

    @JsonProperty
    public Stock getStock() {
        return stock;
    }

    @JsonProperty
    public void setStock(Stock stock) {
        this.stock = stock;
    }

    @JsonProperty
    public List<News> getNews() {
        return news;
    }

    @JsonProperty
    public void setNews(List<News> news) {
        this.news = news;
    }
}