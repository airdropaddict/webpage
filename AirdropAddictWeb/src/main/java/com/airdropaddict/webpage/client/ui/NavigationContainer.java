package com.airdropaddict.webpage.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class NavigationContainer extends Composite {
	private static NavigationContainerUiBinder uiBinder = GWT.create(NavigationContainerUiBinder.class);

	interface NavigationContainerUiBinder extends UiBinder<Widget, NavigationContainer> {
	}

	public NavigationContainer() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
