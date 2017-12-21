package com.airdropaddict.webpage.client;

import com.airdropaddict.webpage.client.ui.WebpageView;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

public class AirdropAddictWeb implements EntryPoint {
	public void onModuleLoad() {
		RootPanel.get().add(new WebpageView());
	}
}
