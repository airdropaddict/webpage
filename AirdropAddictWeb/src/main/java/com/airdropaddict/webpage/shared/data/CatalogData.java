package com.airdropaddict.webpage.shared.data;

import java.io.Serializable;

public class CatalogData extends EntityData implements Serializable {
    private String code;
    private String name;
    private CatalogType catalogType;

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
