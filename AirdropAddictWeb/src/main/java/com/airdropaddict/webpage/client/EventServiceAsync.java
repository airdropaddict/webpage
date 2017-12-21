package com.airdropaddict.webpage.client;

import com.airdropaddict.webpage.shared.EventResultType;
import com.airdropaddict.webpage.shared.data.*;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface EventServiceAsync {
    void initializeCatalogs(AsyncCallback<Boolean> callback);
    void loadCatalogByType(CatalogType catalogType, AsyncCallback<List<CatalogData>> callback);
    void getCatalogByTypeAndCode(CatalogType catalogType, String code, AsyncCallback<CatalogData> callback);

    void getTaskTemplates(AsyncCallback<List<TaskTemplateData>> callback);
    void saveTaskTemplate(TaskTemplateData taskTemplate, AsyncCallback<Void> callback);
    void deleteTaskTemplate(long taskTemplateId, AsyncCallback<Void> callback);

    void getUserByEmail(String email, AsyncCallback<UserData> callback);
    void saveUser(UserData user, AsyncCallback<Long> callback);

    void getEventById(long id, AccessData access, AsyncCallback<EventData> callback);
    void saveEvent(EventData event, AsyncCallback<Long> callback);
    void updateEvent(EventData event, AsyncCallback<Void> callback);
    void deleteEvent(long id, AsyncCallback<Void> callback);
    void getActiveEvents(String eventTypeCode, AccessData access, AsyncCallback<List<EventData>> callback);
    void findEvents(String eventTypeCode, EventResultType resultType, int resultsPerPage, int page, AccessData access, AsyncCallback<List<EventData>> callback);
    void rateEvent(long eventId, int rating, AccessData access, AsyncCallback<EventData> callback);
}