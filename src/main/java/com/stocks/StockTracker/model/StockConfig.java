package com.stocks.StockTracker.model;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class StockConfig extends Configuration{
    @NotEmpty
    private String version;
    private String envFile;

    @Valid
    @NotNull
    @JsonProperty
    private String dataInput;
    
    @JsonProperty
    public String getDataInput() {
        return dataInput;
    }
    
    @JsonProperty
    public void setDataInput(String dataInput) {
        this.dataInput = dataInput;
    }
    
    @JsonProperty
    public String getVersion() {
        return version;
    }
    
    @JsonProperty
    public void setVersion(String version) {
        this.version = version;
    }

    @JsonProperty
    public String getEnvFile() {
        return envFile;
    }

    @JsonProperty
    public void setEnvFile(String envFile) {
        this.envFile = envFile;
    }
}
