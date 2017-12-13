package com.airdropaddict.webpage.shared.data;

import java.io.Serializable;

public class EventParamData implements Serializable {
    private long id;
    private CatalogData paramType;
    private String paramValue;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public CatalogData getParamType() {
        return paramType;
    }

    public void setParamType(CatalogData paramType) {
        this.paramType = paramType;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }
}
