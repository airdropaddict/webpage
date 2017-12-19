package com.airdropaddict.webpage.server;

import com.airdropaddict.webpage.server.entity.BasicEntity;
import com.airdropaddict.webpage.server.entity.CatalogEntity;
import com.airdropaddict.webpage.shared.data.CatalogType;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import java.util.List;

public class BaseDao {
    private static BaseDao airdropDao;

    private BaseDao() {
    }

    public static BaseDao getInstance() {
        if (airdropDao == null)
            airdropDao = new BaseDao();
        return airdropDao;
    }

    public CatalogEntity getBasicCatalog(CatalogType catalogType)
    {
        return ObjectifyService.ofy()
                .load()
                .type(CatalogEntity.class)
                .filter("catalogType", catalogType)
                .first()
                .now();
    }

    public CatalogEntity getCatalogByTypeAndCode(CatalogType catalogType, String code)
    {
        return ObjectifyService.ofy()
                .load()
                .type(CatalogEntity.class)
                .filter("catalogType", catalogType)
                .filter("code", code)
                .first()
                .now();
    }

    public List<CatalogEntity> loadCompleteCatalog()
    {
        return ObjectifyService.ofy()
                .load()
                .type(CatalogEntity.class)
                .list();
    }

    public List<CatalogEntity> loadCompleteCatalogByType(CatalogType catalogType)
    {
        return ObjectifyService.ofy()
                .load()
                .type(CatalogEntity.class)
                .filter("catalogType", catalogType)
                .list();
    }

    public <T extends BasicEntity> T load(Class<T> entityClass, long id)
    {
        return ObjectifyService.ofy().load().type(entityClass).filterKey(Key.create(entityClass, id)).first().now();
    }

    public void save(BasicEntity entity)
    {
        ObjectifyService.ofy().save().entity(entity).now();
    }
}
