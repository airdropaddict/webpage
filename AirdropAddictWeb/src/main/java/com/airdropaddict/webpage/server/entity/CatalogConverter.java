package com.airdropaddict.webpage.server.entity;

import com.airdropaddict.webpage.shared.data.CatalogData;

import java.util.function.Function;

public class CatalogConverter implements Function<CatalogEntity, CatalogData> {
    @Override
    public CatalogData apply(CatalogEntity entity) {
        CatalogData catalog = new CatalogData();
        catalog.setId(entity.getId());
        catalog.setCode(entity.getCode());
        catalog.setName(entity.getName());
        catalog.setCatalogType(entity.getCatalogType());
        return catalog;
    }
}

//new CatalogConverter2(CatalogData::new);

//class CatalogConverter2 implements Function<CatalogEntity, CatalogData> {
//
//    Supplier<CatalogData> supplier;
//
//    public CatalogConverter2(Supplier<CatalogData> supplier) {
//        this.supplier = supplier;
//    }
//
//    @Override
//    public CatalogData apply(CatalogEntity entity) {
//        CatalogData catalog = new CatalogData();
//        catalog.setId(entity.getId());
//        catalog.setCode(entity.getCode());
//        catalog.setName(entity.getName());
//        catalog.setCatalogType(entity.getCatalogType());
//        return catalog;
//    }
//}
