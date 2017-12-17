package com.airdropaddict.webpage.client.ui;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class AirdropPanel extends HTML {
	private HTML panel;
	private HTML header;
	private HTML body;
	private HTML footer;

	public AirdropPanel(Widget parent) {
		super();
		setStyleName("col-lg-3");
		addStyleName("col-md-6");

		panel = new HTML();
		panel.setStyleName("panel");
		panel.addStyleName("panel-primary");
		DOM.appendChild(getElement(), panel.getElement());

		header = new HTML();
		header.setStyleName("panel-heading");
		DOM.appendChild(panel.getElement(), header.getElement());

		body = new HTML();
		body.setStyleName("panel-body");
		DOM.appendChild(panel.getElement(), body.getElement());

		footer = new HTML();
		footer.setStyleName("panel-footer");
		DOM.appendChild(panel.getElement(), footer.getElement());

		DOM.appendChild(parent.getElement(), getElement());
	}
}
