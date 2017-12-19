package com.airdropaddict.webpage.client;

import com.airdropaddict.webpage.shared.data.CatalogData;
import com.airdropaddict.webpage.shared.data.CatalogType;
import com.airdropaddict.webpage.shared.data.EventData;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("event")
public interface EventService extends RemoteService
{
    boolean initializeCatalogs() throws IllegalArgumentException;
    List<CatalogData> loadCatalogByType(CatalogType catalogType) throws IllegalArgumentException;
    CatalogData getCatalogByTypeAndCode(CatalogType catalogType, String code) throws IllegalArgumentException;

    EventData getEventById(long id) throws IllegalArgumentException;
    long saveEvent(EventData event) throws IllegalArgumentException;
    void updateEvent(EventData event) throws IllegalArgumentException;
    void deleteEvent(EventData event) throws IllegalArgumentException;
    List<EventData> getActiveEvents(String eventTypeCode) throws IllegalArgumentException;
}
