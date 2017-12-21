package com.airdropaddict.webpage.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class WebpageView extends Composite {
	private static WebpageViewUiBinder uiBinder = GWT.create(WebpageViewUiBinder.class);

	interface WebpageViewUiBinder extends UiBinder<Widget, WebpageView> {
	}

	public WebpageView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
