package com.airdropaddict.webpage.server;

import com.airdropaddict.webpage.client.EventService;
import com.airdropaddict.webpage.server.entity.CatalogEntity;
import com.airdropaddict.webpage.server.entity.EventEntity;
import com.airdropaddict.webpage.server.entity.UserEntity;
import com.airdropaddict.webpage.shared.data.*;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class EventServiceTest {
    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    private Closeable closeable;
    private EventService eventService;
    private int eventCounter;
    private UserData defaultUser;

    @Before
    public void setUp() {
        helper.setUp();
        closeable = ObjectifyService.begin();
        ObjectifyService.register(CatalogEntity.class);
        ObjectifyService.register(UserEntity.class);
        ObjectifyService.register(EventEntity.class);
        eventService = new EventServiceImpl();
        eventService.initializeCatalogs();
        defaultUser = eventService.getUserByEmail("info@airdropaddict.com");
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
        assertTrue("Nonzero id should be returned", id != 0);

        AccessData accessData = prepareAnonymousAccessData();
        EventData returnedEvent = eventService.getEventById(id, accessData);
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
        AccessData accessData = prepareAnonymousAccessData();
        List<EventData> events = eventService.getActiveEvents(airdropEventType.getCode(), accessData);
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

    @Test
    public void updateEvent() {
        AccessData accessData = prepareAnonymousAccessData();
        CatalogData airdropEventType = fetchAirdropEventType();
        EventData event = prepareTestEvent();
        long id = eventService.saveEvent(event);
        assertTrue("Nonzero id should be returned", id != 0);

        List<EventData> events = eventService.getActiveEvents(airdropEventType.getCode(), accessData);
        int firstSize = events.size();
        assertNotEquals("Event list should not be empty", 0, firstSize);
        EventData loadedEvent = events.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
        assertNotNull("Event not found", loadedEvent);

        loadedEvent.setName("Altered " + loadedEvent.getName());
        loadedEvent.setDescription("Altered " + loadedEvent.getDescription());
        eventService.updateEvent(loadedEvent);

        events = eventService.getActiveEvents(airdropEventType.getCode(), accessData);
        int secondSize = events.size();
        assertEquals("Event list should not change in size", firstSize, secondSize);
        EventData changedEvent = events.stream().filter(e -> e.getId() == id).findFirst().orElse(null);
        assertNotNull("Event not found", loadedEvent);
        assertEquals("Name should be changed", loadedEvent.getName(), changedEvent.getName());
        assertEquals("Description should be changed", loadedEvent.getDescription(), changedEvent.getDescription());
    }

    @Test
    public void deleteEvent()
    {
        AccessData accessData = prepareAnonymousAccessData();
        CatalogData airdropEventType = fetchAirdropEventType();
        EventData event = prepareTestEvent();
        long id = eventService.saveEvent(event);
        assertTrue("Nonzero id should be returned", id != 0);

        eventService.deleteEvent(id);
        EventData loadedEvent = eventService.getEventById(id, accessData);
        assertNull("Event should not exist anymore", loadedEvent);
    }

    @Test
    public void rateEvent()
    {
        AccessData accessData = prepareAnonymousAccessData();
        EventData event = prepareTestEvent();
        long id = eventService.saveEvent(event);
        assertTrue("Nonzero id should be returned", id != 0);
        // A Rates 4
        EventData updatedEvent = eventService.rateEvent(id, 4, accessData);
        assertTrue("Rating has invalid value", updatedEvent.getRating() == 4);
        assertNotNull("Rating status is missing", updatedEvent.getRateStatus());
        assertEquals("Rating status rating has invalid value", 4, updatedEvent.getRateStatus().getRating());
        assertEquals("Rating status ip has invalid value", accessData.getIp(), updatedEvent.getRateStatus().getIp());
        assertFalse("Rating status should not be changeable", updatedEvent.getRateStatus().isChangeable());

        // A rates 2 -> forbidden
        updatedEvent = eventService.rateEvent(id, 2, accessData);
        assertTrue("Rating has invalid value", updatedEvent.getRating() == 4);
        assertNotNull("Rating status is missing", updatedEvent.getRateStatus());
        assertEquals("Rating status rating has invalid value", 4, updatedEvent.getRateStatus().getRating());
        assertEquals("Rating status ip has invalid value", accessData.getIp(), updatedEvent.getRateStatus().getIp());
        assertFalse("Rating status should not be changeable", updatedEvent.getRateStatus().isChangeable());

        // B rates 2 -> OK, rating = 3
        accessData.setIp("192.168.66.1");
        updatedEvent = eventService.rateEvent(id, 2, accessData);
        assertTrue("Rating has invalid value", updatedEvent.getRating() == 3);
        assertNotNull("Rating status is missing", updatedEvent.getRateStatus());
        assertEquals("Rating status rating has invalid value", 2, updatedEvent.getRateStatus().getRating());
        assertEquals("Rating status ip has invalid value", accessData.getIp(), updatedEvent.getRateStatus().getIp());
        assertFalse("Rating status should not be changeable", updatedEvent.getRateStatus().isChangeable());

        // A signs in and rates 2 -> OK, rating = 2
        accessData = prepareUserAccessData();
        updatedEvent = eventService.rateEvent(id, 2, accessData);
        assertTrue("Rating has invalid value", updatedEvent.getRating() == 2);
        assertNotNull("Rating status is missing", updatedEvent.getRateStatus());
        assertEquals("Rating status rating has invalid value", 2, updatedEvent.getRateStatus().getRating());
        assertEquals("Rating status ip has invalid value", accessData.getIp(), updatedEvent.getRateStatus().getIp());

        // A changes IP and rates 4 -> OK, rating = 3
        accessData.setIp("128.66.1.1");
        updatedEvent = eventService.rateEvent(id, 4, accessData);
        assertTrue("Rating has invalid value", updatedEvent.getRating() == 3);
        assertNotNull("Rating status is missing", updatedEvent.getRateStatus());
        assertEquals("Rating status rating has invalid value", 4, updatedEvent.getRateStatus().getRating());
        assertEquals("Rating status ip has invalid value", accessData.getIp(), updatedEvent.getRateStatus().getIp());
    }

    @Test
    public void saveTasks()
    {
        AccessData accessData = prepareAnonymousAccessData();
        EventData event = prepareTestEvent();
        long id = eventService.saveEvent(event);
        assertTrue("Nonzero id should be returned", id != 0);
        event = eventService.getEventById(id, accessData);
        event.getTasks().put("TEST", "TestValue1");
        event.getTasks().put("ANOTHERTEST", "TestValue2");
        eventService.saveEvent(event);
        event = eventService.getEventById(id, accessData);
        assertEquals("Invalid number of tasks", 2, event.getTasks().size());
        assertEquals("Invalid task value", "TestValue1", event.getTasks().get("TEST"));
        assertEquals("Invalid task value", "TestValue2", event.getTasks().get("ANOTHERTEST"));
        event.getTasks().put("TEST", "TestV@lue111");
        event.getTasks().put("THIRDTEST","testValue3");
        eventService.saveEvent(event);
        event = eventService.getEventById(id, accessData);
        assertEquals("Invalid number of tasks", 3, event.getTasks().size());
        assertEquals("Invalid task value", "TestV@lue111", event.getTasks().get("TEST"));
        assertEquals("Invalid task value", "TestValue2", event.getTasks().get("ANOTHERTEST"));
        assertEquals("Invalid task value", "testValue3", event.getTasks().get("THIRDTEST"));
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

    private AccessData prepareAnonymousAccessData() {
        AccessData accessData = new AccessData();
        accessData.setIp("127.0.0.1");
        return accessData;
    }

    private AccessData prepareUserAccessData() {
        AccessData accessData = new AccessData();
        accessData.setIp("127.0.0.1");
        accessData.setUser(defaultUser);
        return accessData;
    }
}
