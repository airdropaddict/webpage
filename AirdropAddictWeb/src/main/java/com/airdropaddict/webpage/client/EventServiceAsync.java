package com.airdropaddict.webpage.client;

import com.airdropaddict.webpage.shared.data.EventData;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface EventServiceAsync {
    void getActiveEvents(long eventType, AsyncCallback<List<EventData>> callback) throws IllegalArgumentException;
    void addEvent(EventData event, AsyncCallback<Void> callback) throws IllegalArgumentException;
    void updateEvent(EventData event, AsyncCallback<Void> callback) throws IllegalArgumentException;
    void deleveEvent(EventData event, AsyncCallback<Void> callback) throws IllegalArgumentException;
}