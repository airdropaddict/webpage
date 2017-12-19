package com.airdropaddict.webpage.shared.data;

import java.io.Serializable;

public class CatalogData implements Serializable {
    private long id;
    private String code;
    private String name;
    private CatalogType catalogType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CatalogType getCatalogType() {
        return catalogType;
    }

    public void setCatalogType(CatalogType catalogType) {
        this.catalogType = catalogType;
    }
}
