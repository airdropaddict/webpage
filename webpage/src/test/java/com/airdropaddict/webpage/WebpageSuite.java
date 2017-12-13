package com.airdropaddict.webpage;

import com.airdropaddict.webpage.client.WebpageTest;
import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;
import junit.framework.TestSuite;

public class WebpageSuite extends GWTTestSuite {
	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for Webpage");
		suite.addTestSuite(WebpageTest.class);
		return suite;
	}
}
