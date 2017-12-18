package com.airdropaddict.webpage.client.ui;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class AirdropsRowPanel extends HTML {
	public AirdropsRowPanel(Widget parent) {
		setStyleName("row");
		DOM.appendChild(parent.getElement(), getElement());
	}
}
