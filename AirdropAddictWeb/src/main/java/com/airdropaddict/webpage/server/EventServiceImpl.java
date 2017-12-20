package com.airdropaddict.webpage.server;

import com.airdropaddict.webpage.client.EventService;
import com.airdropaddict.webpage.server.entity.CatalogEntity;
import com.airdropaddict.webpage.server.entity.RateInfo;
import com.airdropaddict.webpage.server.entity.UserEntity;
import com.airdropaddict.webpage.shared.data.*;
import com.airdropaddict.webpage.server.entity.EventEntity;
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
        UserEntity user = new UserEntity();
        user.setEmail("info@airdropaddict.com");
        BaseDao.getInstance().save(user);
        return appended;
    }

    @Override
    public List<CatalogData> loadCatalogByType(CatalogType catalogType) throws IllegalArgumentException {
        List<CatalogEntity> catalog = BaseDao.getInstance().loadCompleteCatalogByType(catalogType);
        return catalog.stream().map(CatalogEntity::toData).collect(Collectors.toList());
    }

    @Override
    public CatalogData getCatalogByTypeAndCode(CatalogType catalogType, String code) throws IllegalArgumentException {
        CatalogEntity catalog = BaseDao.getInstance().getCatalogByTypeAndCode(catalogType, code);
        return catalog == null ? null : catalog.toData();
    }

    @Override
    public UserData getUserByEmail(String email) throws IllegalArgumentException {
        UserEntity user = BaseDao.getInstance().getUserByEmail(email);
        return user == null ? null : user.toData();
    }

    @Override
    public long saveUser(UserData user) throws IllegalArgumentException {
        return 0;
    }

    @Override
    public EventData getEventById(long id, AccessData access) throws IllegalArgumentException {
        EventEntity event = BaseDao.getInstance().load(EventEntity.class, id);
        return event == null ? null : event.toData(access);
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
        if (event.getId() == 0) {
            throw new IllegalArgumentException("Can not update non-persistent object. Id is 0.");
        }
        if (Tools.isEmpty(event.getName())) {
            throw new IllegalArgumentException("Empty event name was given.");
        }
        EventEntity eventEntity = BaseDao.getInstance().load(EventEntity.class, event.getId());
        eventEntity.setName(event.getName());
        eventEntity.setDescription(event.getDescription());
        eventEntity.setStartTimestamp(event.getStartTimestamp());
        eventEntity.setEndTimestamp(event.getEndTimestamp());
        BaseDao.getInstance().save(eventEntity);
    }

    @Override
    public void deleteEvent(long id) throws IllegalArgumentException {
        BaseDao.getInstance().delete(EventEntity.class, id);
    }

    @Override
    public List<EventData> getActiveEvents(String eventTypeCode, AccessData access) throws IllegalArgumentException {
        try {
            CatalogEntity eventType = BaseDao.getInstance().getCatalogByTypeAndCode(CatalogType.EVENT_TYPE, eventTypeCode);
            Date serverTimestamp = new Date();
            // Get events filtered by endTimestamp
            List<EventEntity> events = BaseDao.getInstance().loadEventsByEventType(eventType, serverTimestamp);
            // Filter in memory by startTimestamp
            return events.stream().filter(e -> e.getStartTimestamp().before(serverTimestamp)).map(e -> e.toData(access)).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public EventData rateEvent(long eventId, int rating, AccessData access) throws IllegalArgumentException {
        EventEntity event = BaseDao.getInstance().load(EventEntity.class, eventId);
        if (event.canRate(access))
        {
            boolean insert = false;
            RateInfo rateInfo = null;
            if (access.getUser() != null) {
                // Find rate by user
                rateInfo = event.getRateHistory().stream()
                        .filter(r -> r.getUser() != null && access.getUser().getId() == r.getUser().get().getId())
                        .findFirst().orElse(null);
                if (rateInfo == null) {
                    // Find rate by IP
                    rateInfo = event.getRateHistory().stream()
                            .filter(r -> access.getIp() == r.getIp())
                            .findFirst().orElse(null);
                }
            }
            // No rate found - create new
            if (rateInfo == null) {
                rateInfo = new RateInfo();
                insert = true;
            }
            // Set user if unset and userinfo is present
            if (rateInfo.getUser() == null && access.getUser() != null) {
                UserEntity user = BaseDao.getInstance().load(UserEntity.class, access.getUser().getId());
                rateInfo.setUser(Ref.create(user));
            }
            rateInfo.setRating(rating);
            rateInfo.setIp(access.getIp());
            if (insert) {
                event.getRateHistory().add(rateInfo);
            }
        }
        event.setRating((float)event.getRateHistory().stream().mapToInt(RateInfo::getRating).average().getAsDouble());
        BaseDao.getInstance().save(event);
        return event.toData(access);
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
