package com.airdropaddict.webpage.client;

import com.airdropaddict.webpage.shared.data.EventData;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("event")
public interface EventService extends RemoteService
{
    List<EventData> getActiveEvents(long eventType) throws IllegalArgumentException;
    void addEvent(EventData event) throws IllegalArgumentException;
    void updateEvent(EventData event) throws IllegalArgumentException;
    void deleveEvent(EventData event) throws IllegalArgumentException;
}
