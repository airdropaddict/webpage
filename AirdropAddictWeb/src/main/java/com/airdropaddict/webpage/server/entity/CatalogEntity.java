package com.airdropaddict.webpage.server.entity;

import com.airdropaddict.webpage.shared.data.CatalogData;
import com.airdropaddict.webpage.shared.data.CatalogType;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

@Entity
public class CatalogEntity extends BasicEntity {
    @Index
    String code;
    String name;
    @Index
    CatalogType catalogType;

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

    public CatalogData toData() {
        CatalogData catalog = new CatalogData();
        catalog.setId(id);
        catalog.setCode(code);
        catalog.setName(name);
        catalog.setCatalogType(catalogType);
        return catalog;
    }
}
