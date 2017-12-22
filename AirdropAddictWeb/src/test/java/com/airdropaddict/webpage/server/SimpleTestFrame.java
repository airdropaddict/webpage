package com.airdropaddict.webpage.server;

import com.airdropaddict.webpage.client.EventService;
import com.airdropaddict.webpage.server.listener.OfyListener;
import com.airdropaddict.webpage.shared.data.UserData;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;
import org.junit.After;
import org.junit.Before;

public class SimpleTestFrame {
    protected LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    private Closeable closeable;
    protected EventService eventService;
    protected static int eventCounter;
    protected static int ipNumber;
    protected static UserData defaultUser;

    @Before
    public void setUp() {
        helper.setUp();
        closeable = ObjectifyService.begin();
        OfyListener.registerObjectifyEntites();
        eventService = new EventServiceImpl();
        eventService.initializeCatalogs();
        defaultUser = eventService.getUserByEmail("info@airdropaddict.com");
    }

    @After
    public void tearDown() {
        closeable.close();
        helper.tearDown();
    }
}
