package com.airdropaddict.webpage.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class InfoCard extends Composite {
	private static InfoCardUiBinder uiBinder = GWT.create(InfoCardUiBinder.class);

	interface InfoCardUiBinder extends UiBinder<Widget, InfoCard> {
	}

	public InfoCard() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
