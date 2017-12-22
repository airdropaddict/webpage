package com.airdropaddict.webpage.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class UntrustedAirdropsContainer extends Composite {
	private static UntrustedAirdropsContainerUiBinder uiBinder = GWT.create(UntrustedAirdropsContainerUiBinder.class);

	interface UntrustedAirdropsContainerUiBinder extends UiBinder<Widget, UntrustedAirdropsContainer> {
	}

	public UntrustedAirdropsContainer() {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
