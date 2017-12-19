package com.airdropaddict.webpage.server;

import com.airdropaddict.webpage.client.EventService;
import com.airdropaddict.webpage.server.entity.CatalogEntity;
import com.airdropaddict.webpage.server.entity.EventEntity;
import com.airdropaddict.webpage.shared.data.CatalogData;
import com.airdropaddict.webpage.shared.data.CatalogType;
import com.airdropaddict.webpage.shared.data.EventData;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class EventServiceTest {
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    private Closeable closeable;
    private EventService eventService;
    private int eventCounter;

    @Before
    public void setUp() {
        helper.setUp();
//        ObjectifyRegistrar.registerDataModel();
        closeable = ObjectifyService.begin();
        ObjectifyService.register(CatalogEntity.class);
        ObjectifyService.register(EventEntity.class);
        eventService = new EventServiceImpl();
        eventService.initializeCatalogs();
    }

    @After
    public void tearDown() {
        closeable.close();
        helper.tearDown();
    }

    @Test
    public void initializeCatalogs() {
        CatalogEntity eventCatalogEntry = BaseDao.getInstance().getCatalogByTypeAndCode(CatalogType.CATALOG_TYPE, "EVT");
        assertNotNull("Event catalog entry is missing", eventCatalogEntry);
        CatalogEntity airdropCatalogEntry = BaseDao.getInstance().getCatalogByTypeAndCode(CatalogType.EVENT_TYPE, "AIR");
        assertNotNull("Airdrop catalog entry is missing", airdropCatalogEntry);
    }

    @Test
    public void loadCatalogByType()
    {
        List<CatalogData> catalogTypeCatalog = eventService.loadCatalogByType(CatalogType.CATALOG_TYPE);
        assertTrue("Entries of CatalogType are missing from catalog", catalogTypeCatalog.size() > 0);
        List<CatalogData> eventCatalog = eventService.loadCatalogByType(CatalogType.EVENT_TYPE);
        assertTrue("Entries of EventType are missing from catalog", eventCatalog.size() > 0);
    }

    @Test
    public void saveEvent()
    {
        EventData event = prepareTestEvent();
        long id = eventService.saveEvent(event);
        assertTrue(id != 0);

        EventData returnedEvent = eventService.getEventById(id);
        assertNotNull("No event is returned", event);
        assertTrue("Invalid event is returned (by id)", returnedEvent.getId() == id);
        assertEquals("Event name does not match", event.getName(), returnedEvent.getName());
        assertEquals("Event description does not match", event.getDescription(), returnedEvent.getDescription());
        assertEquals("Event startTimestamp does not match", event.getStartTimestamp(), returnedEvent.getStartTimestamp());
        assertEquals("Event endTimestamp does not match", event.getEndTimestamp(), returnedEvent.getEndTimestamp());
    }

    @Test
    public void getActiveEvents()
    {
        EventData event = prepareTestEvent();
        long id = eventService.saveEvent(event);
        CatalogData airdropEventType = fetchAirdropEventType();
        List<EventData> events = eventService.getActiveEvents(airdropEventType.getCode());
        assertNotEquals("Event list should not be empty", events.size(), 0);
    }

    @Test
    public void saveIllegalEvent()
    {
        EventData event = new EventData();
        try {
            long id = eventService.saveEvent(event);
            assertNotEquals("Empty event was saved", 0);
        } catch (IllegalArgumentException e) {
        }

        CatalogData airdropEventType = fetchAirdropEventType();
        event.setEventType(airdropEventType);
        try {
            long id = eventService.saveEvent(event);
            assertNotEquals("Event with no name was saved", 0);
        } catch (IllegalArgumentException e) {
        }
    }

    private EventData prepareTestEvent() {
        CatalogData airdropEventType = fetchAirdropEventType();
        EventData event = new EventData();
        event.setEventType(airdropEventType);
        event.setName("TestName-" + (++eventCounter));
        event.setDescription("Description");
        Date now = new Date();
        event.setStartTimestamp(now);
        event.setEndTimestamp(new Date(now.getTime() + 1000*60*60*24));
        return event;
    }

    private CatalogData fetchAirdropEventType() {
        return eventService.getCatalogByTypeAndCode(CatalogType.EVENT_TYPE, "AIR");
    }
}
