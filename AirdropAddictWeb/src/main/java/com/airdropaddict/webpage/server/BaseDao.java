package com.airdropaddict.webpage.server;

import com.airdropaddict.webpage.server.entity.BasicEntity;
import com.airdropaddict.webpage.server.entity.CatalogEntity;
import com.airdropaddict.webpage.server.entity.EventEntity;
import com.airdropaddict.webpage.server.entity.UserEntity;
import com.airdropaddict.webpage.shared.data.CatalogType;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;

import java.util.Date;
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

    public CatalogEntity getBasicCatalog(CatalogType catalogType) {
        return ofy().load().type(CatalogEntity.class).filter("catalogType", catalogType).first().now();
    }

    public CatalogEntity getCatalogByTypeAndCode(CatalogType catalogType, String code) {
        return ofy().load().type(CatalogEntity.class).filter("catalogType", catalogType).filter("code", code).first().now();
    }

    public List<CatalogEntity> loadCompleteCatalog() {
        return ObjectifyService.ofy().load().type(CatalogEntity.class).list();
    }

    public List<CatalogEntity> loadCompleteCatalogByType(CatalogType catalogType) {
        return ObjectifyService.ofy().load().type(CatalogEntity.class).filter("catalogType", catalogType).list();
    }

    public <T extends BasicEntity> T load(Class<T> entityClass, long id) {
        return ObjectifyService.ofy().load().type(entityClass).filterKey(Key.create(entityClass, id)).first().now();
    }

    public <T extends BasicEntity> List<T> loadAll(Class<T> entityClass) {
        return ObjectifyService.ofy().load().type(entityClass).list();
    }

    public void save(BasicEntity entity)
    {
        ObjectifyService.ofy().save().entity(entity).now();
    }

    public void delete(Class entityClass, long id)
    {
        BasicEntity entity = load(entityClass, id);
        ObjectifyService.ofy().delete().entity(entity);
    }

    public UserEntity getUserByEmail(String email) {
        return ofy().load().type(UserEntity.class).filter("email", email).first().now();
    }

    public List<EventEntity> loadNonCompletedEvents(CatalogEntity eventType, Date serverTimestamp) {
        return ofy()
                .load()
                .type(EventEntity.class)
                .filter("eventType", Ref.create(eventType))
                .filter("endTimestamp >", serverTimestamp)
                .list();
    }

    public List<EventEntity> loadCompletedEvents(CatalogEntity eventType, Date serverTimestamp) {
        return ofy()
                .load()
                .type(EventEntity.class)
                .filter("eventType", Ref.create(eventType))
                .filter("endTimestamp <=", serverTimestamp)
                .list();
    }

    public Objectify ofy()
    {
        return ObjectifyService.ofy();
    }
}
