package com.airdropaddict.webpage.client;

import com.airdropaddict.webpage.shared.data.*;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("event")
public interface EventService extends RemoteService
{
    boolean initializeCatalogs() throws IllegalArgumentException;
    List<CatalogData> loadCatalogByType(CatalogType catalogType) throws IllegalArgumentException;
    CatalogData getCatalogByTypeAndCode(CatalogType catalogType, String code) throws IllegalArgumentException;

    UserData getUserByEmail(String email) throws IllegalArgumentException;
    long saveUser(UserData user) throws IllegalArgumentException;

    EventData getEventById(long id, AccessData access) throws IllegalArgumentException;
    long saveEvent(EventData event) throws IllegalArgumentException;
    void updateEvent(EventData event) throws IllegalArgumentException;
    void deleteEvent(long id) throws IllegalArgumentException;
    List<EventData> getActiveEvents(String eventTypeCode, AccessData access) throws IllegalArgumentException;
    EventData rateEvent(long eventId, int rating, AccessData access) throws IllegalArgumentException;
}
