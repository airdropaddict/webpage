package com.airdropaddict.webpage.client;

import com.airdropaddict.webpage.server.entity.EventEntity;
import com.airdropaddict.webpage.shared.FieldVerifier;
import com.airdropaddict.webpage.shared.data.CatalogData;
import com.airdropaddict.webpage.shared.data.CatalogType;
import com.airdropaddict.webpage.shared.data.EventData;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

import java.util.Date;
import java.util.List;

/**
 * GWT JUnit tests must extend GWTTestCase.
 */
public class AirdropAddictWebTest extends GWTTestCase {

	private final LocalServiceTestHelper testHelper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());

	public void gwtSetUp() {
		testHelper.setUp();
	}

	public void gwtTearDown() {
		testHelper.tearDown();
	}

	/**
	 * Must refer to a valid module that sources this class.
	 */
	public String getModuleName() {
		return "com.airdropaddict.webpage.AirdropAddictWebJUnit";
	}

	/**
	 * Tests the FieldVerifier.
	 */
	public void testFieldVerifier() {
		assertFalse(FieldVerifier.isValidName(null));
		assertFalse(FieldVerifier.isValidName(""));
		assertFalse(FieldVerifier.isValidName("a"));
		assertFalse(FieldVerifier.isValidName("ab"));
		assertFalse(FieldVerifier.isValidName("abc"));
		assertTrue(FieldVerifier.isValidName("abcd"));
	}

	/**
	 * This test will send a request to the server using the greetServer method in
	 * GreetingService and verify the response.
	 */
	public void testGreetingService() {
		// Create the service that we will test.
		GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
		ServiceDefTarget target = (ServiceDefTarget) greetingService;
		target.setServiceEntryPoint(GWT.getModuleBaseURL() + "airdropaddictweb/greet");

		// Since RPC calls are asynchronous, we will need to wait for a response
		// after this test method returns. This line tells the test runner to wait
		// up to 10 seconds before timing out.
		delayTestFinish(10000);

		// Send a request to the server.
		greetingService.greetServer("GWT User", new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				// The request resulted in an unexpected error.
				fail("Request failure: " + caught.getMessage());
			}

			public void onSuccess(String result) {
				// Verify that the response is correct.
				assertTrue(result.startsWith("Hello, GWT User!"));

				// Now that we have received a response, we need to tell the test runner
				// that the test is complete. You must call finishTest() after an
				// asynchronous test finishes successfully, or the test will time out.
				finishTest();
			}
		});
	}

}
