package com.airdropaddict.webpage.server;

import com.airdropaddict.webpage.client.EventService;
import com.airdropaddict.webpage.server.entity.*;
import com.airdropaddict.webpage.shared.EventResultType;
import com.airdropaddict.webpage.shared.data.*;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class EventServiceImpl extends RemoteServiceServlet implements EventService {

    @Override
    public boolean initializeCatalogs() {
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
    public List<CatalogData> loadCatalogByType(CatalogType catalogType)  {
        List<CatalogEntity> catalog = BaseDao.getInstance().loadCompleteCatalogByType(catalogType);
        return catalog.stream().map(new CatalogConverter()).collect(Collectors.toList());
    }

    @Override
    public CatalogData getCatalogByTypeAndCode(CatalogType catalogType, String code)  {
        CatalogEntity catalog = BaseDao.getInstance().getCatalogByTypeAndCode(catalogType, code);
        return catalog == null ? null : new CatalogConverter().apply(catalog);
    }

    @Override
    public List<TaskTemplateData> getTaskTemplates()  {
        List<TaskTemplateEntity> taskTemplates = BaseDao.getInstance().loadAll(TaskTemplateEntity.class);
        return taskTemplates.stream().map(new TaskTemplateConverter()).collect(Collectors.toList());
    }

    @Override
    public void saveTaskTemplate(TaskTemplateData taskTemplate)  {
        TaskTemplateEntity taskTemplateEntity;
        if (taskTemplate.getId() != 0) {
            taskTemplateEntity = BaseDao.getInstance().load(TaskTemplateEntity.class, taskTemplate.getId());
        }
        else {
            taskTemplateEntity = new TaskTemplateEntity();
        }
        new TaskTemplateUpdater().accept(taskTemplateEntity, taskTemplate);
        BaseDao.getInstance().save(taskTemplateEntity);
    }

    @Override
    public void deleteTaskTemplate(long taskTemplateId)  {
        BaseDao.getInstance().delete(TaskTemplateEntity.class, taskTemplateId);
    }

    @Override
    public UserData getUserByEmail(String email)  {
        UserEntity user = BaseDao.getInstance().getUserByEmail(email);
        return user == null ? null : new UserConverter().apply(user);
    }

    @Override
    public long saveUser(UserData user)  {
        return 0;
    }

    @Override
    public EventData getEventById(long id, AccessData access)  {
        EventEntity event = BaseDao.getInstance().load(EventEntity.class, id);
        return event == null ? null : new EventConverter(access).apply(event);
    }

    @Override
    public long saveEvent(EventData event)  {
        if (event.getEventType() == null) {
            throw new IllegalArgumentException("Empty event type was given.");
        }
        if (Tools.isEmpty(event.getName())) {
            throw new IllegalArgumentException("Empty event name was given.");
        }

        EventEntity eventEntity = new EventEntity();
        new EventUpdater().accept(eventEntity, event);
        BaseDao.getInstance().save(eventEntity);
        return eventEntity.getId();
    }

    @Override
    public void updateEvent(EventData event)  {
        if (event.getId() == 0) {
            throw new IllegalArgumentException("Can not update non-persistent object. Id is 0.");
        }
        if (Tools.isEmpty(event.getName())) {
            throw new IllegalArgumentException("Empty event name was given.");
        }
        EventEntity eventEntity = BaseDao.getInstance().load(EventEntity.class, event.getId());
        new EventUpdater().accept(eventEntity, event);
        BaseDao.getInstance().save(eventEntity);
    }

    @Override
    public void deleteEvent(long id)  {
        BaseDao.getInstance().delete(EventEntity.class, id);
    }

    @Override
    public List<EventData> getActiveEvents(String eventTypeCode, AccessData access)  {
        try {
            CatalogEntity eventType = BaseDao.getInstance().getCatalogByTypeAndCode(CatalogType.EVENT_TYPE, eventTypeCode);
            Date serverTimestamp = new Date();
            // Get events filtered by endTimestamp
            List<EventEntity> events = BaseDao.getInstance().loadNonCompletedEvents(eventType, serverTimestamp);
            // Filter in memory by startTimestamp
            return events.stream()
                    .filter(e -> e.getStartTimestamp().before(serverTimestamp))
                    .map(e -> new EventConverter(access).apply(e))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public List<EventData> findEvents(String eventTypeCode, EventResultType resultType, int resultsPerPage, int page, AccessData access) {
        try {
            CatalogEntity eventType = BaseDao.getInstance().getCatalogByTypeAndCode(CatalogType.EVENT_TYPE, eventTypeCode);
            Date serverTimestamp = new Date();
            int startIndex = resultsPerPage * page;

            List<EventEntity> events;
            if (resultType == EventResultType.EXPIRED) {
                int endIndex = resultsPerPage * (page+1) - 1;
                // Get events filtered by endTimestamp and paged by DB
                events = BaseDao.getInstance().loadCompletedEvents(eventType, serverTimestamp).subList(startIndex, endIndex);
                return events.stream()
                    .map(e -> new EventConverter(access).apply(e))
                    .collect(Collectors.toList());
            }
            else {
                // Get events filtered by endTimestamp
                events = BaseDao.getInstance().loadNonCompletedEvents(eventType, serverTimestamp);
                if (resultType == EventResultType.ACTIVE) {
                    // Filter in memory by startTimestamp and do paging
                    return events.stream()
                        .filter(e -> e.getStartTimestamp().before(serverTimestamp))
                        .skip(startIndex)
                        .limit(resultsPerPage)
                        .map(e -> new EventConverter(access).apply(e))
                        .collect(Collectors.toList());
                }
                else { // EventResultType.WAITING_FOR_ACTIVATION
                    // Filter in memory by startTimestamp and do paging
                    return events.stream()
                        .filter(e -> e.getStartTimestamp().after(serverTimestamp))
                        .skip(startIndex)
                        .limit(resultsPerPage)
                        .map(e -> new EventConverter(access).apply(e))
                        .collect(Collectors.toList());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public EventData rateEvent(long eventId, int rating, AccessData access)  {
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
        return new EventConverter(access).apply(event);
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
