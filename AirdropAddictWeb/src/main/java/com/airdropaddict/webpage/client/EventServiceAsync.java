package com.airdropaddict.webpage.client;

import com.airdropaddict.webpage.shared.data.CatalogData;
import com.airdropaddict.webpage.shared.data.CatalogType;
import com.airdropaddict.webpage.shared.data.EventData;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface EventServiceAsync {
    void initializeCatalogs(AsyncCallback<Boolean> callback) throws IllegalArgumentException;
    void loadCatalogByType(CatalogType catalogType, AsyncCallback<List<CatalogData>> callback) throws IllegalArgumentException;
    void getCatalogByTypeAndCode(CatalogType catalogType, String code, AsyncCallback<CatalogData> callback) throws IllegalArgumentException;

    void getEventById(long id, AsyncCallback<EventData> callback) throws IllegalArgumentException;
    void saveEvent(EventData event, AsyncCallback<Long> callback) throws IllegalArgumentException;
    void updateEvent(EventData event, AsyncCallback<Void> callback) throws IllegalArgumentException;
    void deleteEvent(EventData event, AsyncCallback<Void> callback) throws IllegalArgumentException;
    void getActiveEvents(String eventTypeCode, AsyncCallback<List<EventData>> callback) throws IllegalArgumentException;
}