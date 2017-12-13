package com.airdropaddict.webpage;

import com.airdropaddict.webpage.client.AirdropAddictWebTest;
import com.google.gwt.junit.tools.GWTTestSuite;
import junit.framework.Test;
import junit.framework.TestSuite;

public class AirdropAddictWebSuite extends GWTTestSuite {
	public static Test suite() {
		TestSuite suite = new TestSuite("Tests for AirdropAddictWeb");
		suite.addTestSuite(AirdropAddictWebTest.class);
		return suite;
	}
}
