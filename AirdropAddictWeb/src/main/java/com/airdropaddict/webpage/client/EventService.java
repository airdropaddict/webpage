package com.airdropaddict.webpage.client;

import com.airdropaddict.webpage.shared.EventResultType;
import com.airdropaddict.webpage.shared.data.*;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("event")
public interface EventService extends RemoteService
{
    boolean initializeCatalogs();
    List<CatalogData> loadCatalogByType(CatalogType catalogType);
    CatalogData getCatalogByTypeAndCode(CatalogType catalogType, String code);

    List<TaskTemplateData> getTaskTemplates();
    long saveTaskTemplate(TaskTemplateData taskTemplate);
    void deleteTaskTemplate(long taskTemplateId);

    UserData getUserByEmail(String email);
    long saveUser(UserData user);

    EventData getEventById(long id, AccessData access);
    long saveEvent(EventData event);
    void deleteEvent(long id);
    List<EventData> getActiveEvents(String eventTypeCode, AccessData access);
    PageData<SimpleEventData> getSimplePageableEvents(String eventTypeCode, EventResultType resultType, boolean scam, int resultsPerPage, int page, AccessData access);
    EventData rateEvent(long eventId, int rating, AccessData access);
}
