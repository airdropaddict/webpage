package com.airdropaddict.webpage.server;

import com.airdropaddict.webpage.client.EventService;
import com.airdropaddict.webpage.server.entity.CatalogEntity;
import com.airdropaddict.webpage.server.listener.OfyListener;
import com.airdropaddict.webpage.shared.EventResultType;
import com.airdropaddict.webpage.shared.data.*;
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

public class EventServiceTest extends SimpleTestFrame {

    @Test
    public void initializeCatalogs() {
        CatalogEntity eventCatalogEntry = BaseDao.getInstance().getCatalogByTypeAndCode(CatalogType.CATALOG_TYPE, "EVT");
        assertNotNull("Event catalog entry is missing", eventCatalogEntry);
        CatalogEntity airdropCatalogEntry = BaseDao.getInstance().getCatalogByTypeAndCode(CatalogType.EVENT_TYPE, "AIR");
        assertNotNull("Airdrop catalog entry is missing", airdropCatalogEntry);
        CatalogEntity rateCatalogEntry = BaseDao.getInstance().getCatalogByTypeAndCode(CatalogType.EVENT_VOTE_TYPE, "RAT");
        assertNotNull("Rate catalog entry is missing", rateCatalogEntry);
        CatalogEntity scamCatalogEntry = BaseDao.getInstance().getCatalogByTypeAndCode(CatalogType.EVENT_VOTE_TYPE, "SCM");
        assertNotNull("Scam catalog entry is missing", rateCatalogEntry);
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
        assertNotNull("InsertTimestamp should be set", returnedEvent.getInsertTimestamp());
        assertEquals("Event startTimestamp does not match", event.getStartTimestamp(), returnedEvent.getStartTimestamp());
        assertEquals("Event endTimestamp does not match", event.getEndTimestamp(), returnedEvent.getEndTimestamp());
    }

    @Test
    public void getActiveEvents() {
        EventData event = prepareTestEvent();
        long id = eventService.saveEvent(event);
        CatalogData airdropEventType = fetchAirdropEventType();
        AccessData accessData = prepareAnonymousAccessData();
        List<EventData> events = eventService.getActiveEvents(airdropEventType.getCode(), accessData);
        assertNotEquals("Event list should not be empty", events.size(), 0);
    }

    @Test
    public void findEvents() {
        CatalogData airdropEventType = fetchAirdropEventType();
        AccessData accessData = prepareAnonymousAccessData();
        eventService.saveEvent(prepareTestEvent());
        eventService.saveEvent(prepareTestEvent());
        eventService.saveEvent(prepareTestEvent());
        eventService.saveEvent(prepareTestEvent());
        eventService.saveEvent(prepareTestEvent());
        PageData<SimpleEventData> pageInfo = eventService.getSimplePageableEvents(airdropEventType.getCode(), EventResultType.ACTIVE, false, 3, 0, accessData);
        assertEquals("Invalid result size", 3, pageInfo.getItems().size());
        assertFalse("Should not be last page", pageInfo.isLastPage());
        pageInfo = eventService.getSimplePageableEvents(airdropEventType.getCode(), EventResultType.ACTIVE, false, 3, 1, accessData);
        assertEquals("Invalid result size", 2, pageInfo.getItems().size());
        assertTrue("Should be last page", pageInfo.isLastPage());
        pageInfo = eventService.getSimplePageableEvents(airdropEventType.getCode(), EventResultType.ACTIVE, false, 3, 2, accessData);
        assertEquals("Invalid result size", 0, pageInfo.getItems().size());
        assertTrue("Should be last page", pageInfo.isLastPage());
        pageInfo = eventService.getSimplePageableEvents(airdropEventType.getCode(), EventResultType.ACTIVE, false, 10, 0, accessData);
        assertEquals("Invalid result size", 5, pageInfo.getItems().size());
        assertTrue("Should be last page", pageInfo.isLastPage());
        pageInfo = eventService.getSimplePageableEvents(airdropEventType.getCode(), EventResultType.ACTIVE, false, 10, 99999, accessData);
        assertEquals("Invalid result size", 0, pageInfo.getItems().size());
        assertTrue("Should be last page", pageInfo.isLastPage());
    }

    @Test
    public void findEventsMix() {
        CatalogData airdropEventType = fetchAirdropEventType();
        AccessData accessData = prepareAnonymousAccessData();
        eventService.saveEvent(prepareTestEvent());
        eventService.saveEvent(prepareTestEvent());
        eventService.saveEvent(prepareTestEvent());
        eventService.saveEvent(prepareTestEvent());
        eventService.saveEvent(prepareTestEvent());
        eventService.saveEvent(prepareExpiredEvent());
        eventService.saveEvent(prepareExpiredEvent());
        eventService.saveEvent(prepareFutureEvent());
        eventService.saveEvent(prepareFutureEvent());
        eventService.saveEvent(prepareFutureEvent());
        PageData<SimpleEventData> pageInfo = eventService.getSimplePageableEvents(airdropEventType.getCode(), EventResultType.ACTIVE, false, 3, 0, accessData);
        assertEquals("Invalid result size", 3, pageInfo.getItems().size());
        assertFalse("Should not be last page", pageInfo.isLastPage());
        pageInfo = eventService.getSimplePageableEvents(airdropEventType.getCode(), EventResultType.ACTIVE, false, 3, 1, accessData);
        assertEquals("Invalid result size", 2, pageInfo.getItems().size());
        assertTrue("Should be last page", pageInfo.isLastPage());
        pageInfo = eventService.getSimplePageableEvents(airdropEventType.getCode(), EventResultType.ACTIVE, false, 3, 2, accessData);
        assertEquals("Invalid result size", 0, pageInfo.getItems().size());
        assertTrue("Should be last page", pageInfo.isLastPage());
        pageInfo = eventService.getSimplePageableEvents(airdropEventType.getCode(), EventResultType.ACTIVE, false, 10, 0, accessData);
        assertEquals("Invalid result size", 5, pageInfo.getItems().size());
        assertTrue("Should be last page", pageInfo.isLastPage());
        pageInfo = eventService.getSimplePageableEvents(airdropEventType.getCode(), EventResultType.ACTIVE, false, 10, 99999, accessData);
        assertEquals("Invalid result size", 0, pageInfo.getItems().size());
        assertTrue("Should be last page", pageInfo.isLastPage());
        pageInfo = eventService.getSimplePageableEvents(airdropEventType.getCode(), EventResultType.EXPIRED, false, 2, 0, accessData);
        assertEquals("Invalid result size", 2, pageInfo.getItems().size());
        assertTrue("Should be last page", pageInfo.isLastPage());
        pageInfo = eventService.getSimplePageableEvents(airdropEventType.getCode(), EventResultType.FUTURE, false, 2, 0, accessData);
        assertEquals("Invalid result size", 2, pageInfo.getItems().size());
        assertFalse("Should not be last page", pageInfo.isLastPage());
        pageInfo = eventService.getSimplePageableEvents(airdropEventType.getCode(), EventResultType.FUTURE, false, 2, 1, accessData);
        assertEquals("Invalid result size", 1, pageInfo.getItems().size());
        assertTrue("Should be last page", pageInfo.isLastPage());
    }

    @Test
    public void saveIllegalEvent() {
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
        eventService.saveEvent(loadedEvent);

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
        String ipA = accessData.getIp();
        EventData event = prepareTestEvent();
        long id = eventService.saveEvent(event);
        assertTrue("Nonzero id should be returned", id != 0);
        // A Rates 4
        EventData updatedEvent = eventService.rateEvent(id, 4, accessData);
        assertEquals("Invalid number of rates", 1, updatedEvent.getNumberOfRates());
        assertTrue("Rating has invalid value", updatedEvent.getRating() == 4);
        assertNotNull("Rating status is missing", updatedEvent.getRateStatus());
        assertEquals("Rating status rating has invalid value", 4, updatedEvent.getRateStatus().getRating());
        assertEquals("Rating status ip has invalid value", accessData.getIp(), updatedEvent.getRateStatus().getIp());
        assertFalse("Rating status should not be changeable", updatedEvent.getRateStatus().isChangeable());

        // A rates 2 -> forbidden
        updatedEvent = eventService.rateEvent(id, 2, accessData);
        assertEquals("Invalid number of rates", 1, updatedEvent.getNumberOfRates());
        assertTrue("Rating has invalid value", updatedEvent.getRating() == 4);
        assertNotNull("Rating status is missing", updatedEvent.getRateStatus());
        assertEquals("Rating status rating has invalid value", 4, updatedEvent.getRateStatus().getRating());
        assertEquals("Rating status ip has invalid value", accessData.getIp(), updatedEvent.getRateStatus().getIp());
        assertFalse("Rating status should not be changeable", updatedEvent.getRateStatus().isChangeable());

        // B rates 2 -> OK, rating = 3
        accessData = prepareAnonymousAccessData();
        String ipB = accessData.getIp();
        updatedEvent = eventService.rateEvent(id, 2, accessData);
        assertEquals("Invalid number of rates", 2, updatedEvent.getNumberOfRates());
        assertTrue("Rating has invalid value", updatedEvent.getRating() == 3);
        assertNotNull("Rating status is missing", updatedEvent.getRateStatus());
        assertEquals("Rating status rating has invalid value", 2, updatedEvent.getRateStatus().getRating());
        assertEquals("Rating status ip has invalid value", accessData.getIp(), updatedEvent.getRateStatus().getIp());
        assertFalse("Rating status should not be changeable", updatedEvent.getRateStatus().isChangeable());

        // A signs in and rates 2 -> OK, rating = 2
        accessData = prepareUserAccessData(defaultUser);
        accessData.setIp(ipA);
        updatedEvent = eventService.rateEvent(id, 2, accessData);
        assertEquals("Invalid number of rates", 2, updatedEvent.getNumberOfRates());
        assertTrue("Rating has invalid value", updatedEvent.getRating() == 2);
        assertNotNull("Rating status is missing", updatedEvent.getRateStatus());
        assertEquals("Rating status rating has invalid value", 2, updatedEvent.getRateStatus().getRating());
        assertEquals("Rating status ip has invalid value", accessData.getIp(), updatedEvent.getRateStatus().getIp());

        // A changes IP and rates 4 -> OK, rating = 3
        accessData.setIp(ipB);
        updatedEvent = eventService.rateEvent(id, 4, accessData);
        assertTrue("Rating has invalid value", updatedEvent.getRating() == 3);
        assertEquals("Invalid number of rates", 2, updatedEvent.getNumberOfRates());
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

    @Test
    public void sorting() {
        AccessData accessData = prepareAnonymousAccessData();
        EventData event1 = prepareTestEvent();
        event1.setInsertTimestamp(new Date());
        event1.setId(eventService.saveEvent(event1));
        EventData event2 = prepareTestEvent();
        event2.setInsertTimestamp(new Date(event1.getInsertTimestamp().getTime() + 1000));
        event2.setId(eventService.saveEvent(event2));
        EventData event3 = prepareTestEvent();
        event3.setInsertTimestamp(new Date(event2.getInsertTimestamp().getTime() + 1000));
        event3.setId(eventService.saveEvent(event3));
        PageData<SimpleEventData> page = eventService.getSimplePageableEvents(
                event1.getEventType().getCode(), EventResultType.ACTIVE, false, 10, 0, accessData);
        assertEquals("Event 3 should be first one in results", event3.getId(), page.getItems().get(0).getId());
        assertEquals("Event 2 should be second one in results", event2.getId(), page.getItems().get(1).getId());
        assertEquals("Event 1 should be last one in results", event1.getId(), page.getItems().get(2).getId());

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

    private EventData prepareExpiredEvent() {
        CatalogData airdropEventType = fetchAirdropEventType();
        EventData event = new EventData();
        event.setEventType(airdropEventType);
        event.setName("TestName-" + (++eventCounter));
        event.setDescription("Description");
        Date now = new Date();
        event.setStartTimestamp(new Date(now.getTime() - 2000*60*60*24));
        event.setEndTimestamp(new Date(now.getTime() - 1000*60*60*24));
        return event;
    }

    private EventData prepareFutureEvent() {
        CatalogData airdropEventType = fetchAirdropEventType();
        EventData event = new EventData();
        event.setEventType(airdropEventType);
        event.setName("TestName-" + (++eventCounter));
        event.setDescription("Description");
        Date now = new Date();
        event.setStartTimestamp(new Date(now.getTime() + 1000*60*60*24));
        event.setEndTimestamp(new Date(now.getTime() + 2000*60*60*24));
        return event;
    }

    private CatalogData fetchAirdropEventType() {
        return eventService.getCatalogByTypeAndCode(CatalogType.EVENT_TYPE, "AIR");
    }

    public static AccessData prepareAnonymousAccessData() {
        AccessData accessData = new AccessData();
        accessData.setIp(getNextIpAddress());
        return accessData;
    }

    public static AccessData prepareUserAccessData(UserData user) {
        AccessData accessData = new AccessData();
        accessData.setIp(getNextIpAddress());
        accessData.setUser(user);
        return accessData;
    }

    public static String getNextIpAddress() {
        return "127." + computeIpSubclass(++ipNumber);
    }

    public static String computeIpSubclass(int i) {
        return "" + i/65536 + "." + i/256 + "." + i%256;
    }
}
