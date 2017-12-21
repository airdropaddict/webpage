package com.airdropaddict.webpage.server.entity;

import com.airdropaddict.webpage.shared.data.CatalogData;

import java.util.function.BiConsumer;

public class CatalogUpdater implements BiConsumer<CatalogEntity, CatalogData> {
    @Override
    public void accept(CatalogEntity catalogEntity, CatalogData catalogData) {
        catalogEntity.setCode(catalogData.getCode());
        catalogEntity.setName(catalogData.getName());
    }
}
