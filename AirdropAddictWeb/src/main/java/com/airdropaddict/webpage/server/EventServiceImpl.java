package com.airdropaddict.webpage.server;

import com.airdropaddict.webpage.client.EventService;
import com.airdropaddict.webpage.server.entity.CatalogEntity;
import com.airdropaddict.webpage.shared.data.CatalogType;
import com.airdropaddict.webpage.server.entity.EventEntity;
import com.airdropaddict.webpage.shared.data.CatalogData;
import com.airdropaddict.webpage.shared.data.EventData;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class EventServiceImpl extends RemoteServiceServlet implements EventService {

    @Override
    public boolean initializeCatalogs() throws IllegalArgumentException {
        List<CatalogEntity> catalog = ObjectifyService.ofy()
                .load()
                .type(CatalogEntity.class)
                .list();
        boolean appended = addToCatalogIfNotExists(catalog, CatalogType.CATALOG_TYPE, "EVT", "Event Type");
        appended |= addToCatalogIfNotExists(catalog, CatalogType.EVENT_TYPE, "AIR", "Airdrop");
        return appended;
    }

    @Override
    public List<CatalogData> loadCatalogByType(CatalogType catalogType) throws IllegalArgumentException {
        List<CatalogEntity> catalog = BaseDao.getInstance().loadCompleteCatalogByType(catalogType);
        return catalog.stream().map(CatalogEntity::toData).collect(Collectors.toList());
    }

    @Override
    public CatalogData getCatalogByTypeAndCode(CatalogType catalogType, String code) throws IllegalArgumentException {
        return BaseDao.getInstance().getCatalogByTypeAndCode(catalogType, code).toData();
    }

    @Override
    public EventData getEventById(long id) throws IllegalArgumentException {
        return BaseDao.getInstance().load(EventEntity.class, id).toData();
    }

    @Override
    public long saveEvent(EventData event) throws IllegalArgumentException {
        if (event.getEventType() == null) {
            throw new IllegalArgumentException("Empty event type was given.");
        }
        if (Tools.isEmpty(event.getName())) {
            throw new IllegalArgumentException("Empty event name was given.");
        }

        CatalogEntity eventType = BaseDao.getInstance().getCatalogByTypeAndCode(CatalogType.EVENT_TYPE, event.getEventType().getCode());
        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventType(Ref.create(eventType));
        eventEntity.setName(event.getName());
        eventEntity.setDescription(event.getDescription());
        eventEntity.setStartTimestamp(event.getStartTimestamp());
        eventEntity.setEndTimestamp(event.getEndTimestamp());
        BaseDao.getInstance().save(eventEntity);
        return eventEntity.getId();
    }

    @Override
    public void updateEvent(EventData event) throws IllegalArgumentException {

    }

    @Override
    public void deleteEvent(EventData event) throws IllegalArgumentException {

    }

    @Override
    public List<EventData> getActiveEvents(String eventTypeCode) throws IllegalArgumentException {
        try {
            CatalogEntity eventType = BaseDao.getInstance().getCatalogByTypeAndCode(CatalogType.EVENT_TYPE, eventTypeCode);
            Date serverTimestamp = new Date();
            // Get events filtered by endTimestamp
            List<EventEntity> events = BaseDao.getInstance().loadEventsByEventType(eventType, serverTimestamp);
            // Filter in memory by startTimestamp
            return events.stream().filter(e -> e.getStartTimestamp().before(serverTimestamp)).map(EventEntity::toData).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private boolean addToCatalogIfNotExists(List<CatalogEntity> catalog, CatalogType catalogType, String code, String name) {
        if (catalogType == null || code == null) {
            return false;
        }
        CatalogEntity entry = findCatalogEntry(catalog, catalogType, code);
        if (entry == null)
        {
            entry = new CatalogEntity();
            entry.setCatalogType(catalogType);
            entry.setCode(code);
            entry.setName(name);
            BaseDao.getInstance().save(entry);
            return true;
        }
        return false;
    }

    private CatalogEntity findCatalogEntry(List<CatalogEntity> catalog, CatalogType catalogType, String code)
    {
        return catalog.stream().filter(c -> catalogType.equals(c.getCatalogType()) && code.equals(c.getCode())).findFirst().orElse(null);
    }
}
