package com.airdropaddict.webpage.server;

import com.airdropaddict.webpage.shared.EventResultType;
import com.airdropaddict.webpage.shared.data.AccessData;
import com.airdropaddict.webpage.shared.data.CatalogData;
import com.airdropaddict.webpage.shared.data.CatalogType;
import com.airdropaddict.webpage.shared.data.EventData;
import org.junit.Test;

import java.util.Date;

import static com.airdropaddict.webpage.server.EventServiceTest.prepareAnonymousAccessData;

public class EventServiceLoadTests extends SimpleTestFrame {

    @Test
    public void loadRatingTest()
    {
        EventData[] events = new EventData[100];
        AccessData[] accesses = new AccessData[100];
        System.out.println("Generating events");
        long start = System.currentTimeMillis();
        for (int i = 0; i < events.length; i++)
        {
            events[i] = prepareTestEvent();
            long id = eventService.saveEvent(events[i]);
            events[i].setId(id);
        }
        System.out.println("Saving took: " + (System.currentTimeMillis()-start) + " ms, which means " + ((System.currentTimeMillis()-start)/events.length) + " ms per operation");
        System.out.println("Generating accesses");
        for (int i = 0; i < accesses.length; i++)
        {
            accesses[i] = prepareAnonymousAccessData();
        }
        System.out.println("Rating: " + accesses.length + " accesses on " + events.length + " events");
        start = System.currentTimeMillis();
        for (int i = 0; i < events.length; i++)
        {
            System.out.println("Rating for event: " + i);
            for (int j = 0; j < accesses.length; j++) {
                eventService.rateEvent(events[i].getId(), (int)(Math.random()*5+1), accesses[j]);
            }
        }
        System.out.println("Done. Rating took: " + (System.currentTimeMillis()-start) + " ms, that is " + ((System.currentTimeMillis()-start) / accesses.length / events.length) + " ms per operation.");

        start = System.currentTimeMillis();
        EventData event = eventService.getEventById(events[66].getId(), accesses[0]);
        System.out.println("Load took: " + (System.currentTimeMillis()-start) + " ms");

        CatalogData airdropEventType = fetchAirdropEventType();
        start = System.currentTimeMillis();
        eventService.getActiveEvents(airdropEventType.getCode(), accesses[0]);
        System.out.println("Load of all active events took: " + (System.currentTimeMillis()-start) + " ms");

        start = System.currentTimeMillis();
        eventService.getSimplePageableEvents(airdropEventType.getCode(), EventResultType.ACTIVE, false, 20, 3, accesses[0]);
        System.out.println("Load of page 3 for events took: " + (System.currentTimeMillis()-start) + " ms");
    }

    private CatalogData fetchAirdropEventType() {
        return eventService.getCatalogByTypeAndCode(CatalogType.EVENT_TYPE, "AIR");
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
}
