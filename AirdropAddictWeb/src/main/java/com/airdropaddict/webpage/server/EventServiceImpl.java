package com.airdropaddict.webpage.server;

import com.airdropaddict.webpage.client.EventService;
import com.airdropaddict.webpage.shared.data.EventData;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import java.util.LinkedList;
import java.util.List;

public class EventServiceImpl extends RemoteServiceServlet implements EventService {

//    private static final PersistenceManagerFactory PMF = JDOHelper.getPersistenceManagerFactory("transactions-optional");

    @Override
    public List<EventData> getActiveEvents(long eventType) throws IllegalArgumentException {
        List<EventData> events = new LinkedList<>();
        EventData event = new EventData();
        event.setName("Test");
        event.setDescription("Description");
        events.add(event);
        return events;
    }

    @Override
    public void addEvent(EventData event) throws IllegalArgumentException {

    }

    @Override
    public void updateEvent(EventData event) throws IllegalArgumentException {

    }

    @Override
    public void deleveEvent(EventData event) throws IllegalArgumentException {

    }
}
