package com.airdropaddict.webpage.client.ui;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class AirdropInfoPanel extends HTML {
	private HTML panel;
	private HTML header;
	private HTML body;
	private HTML footer;

	public AirdropInfoPanel(Widget parent) {
		super();
		setStyleName("col-lg-3");
		addStyleName("col-md-6");

		panel = new HTML();
		panel.setStyleName("panel");
		panel.addStyleName("panel-primary");
		DOM.appendChild(getElement(), panel.getElement());

		header = new HTML();
		header.setStyleName("panel-heading");
		header.setText("Primary Panel GWT");
		DOM.appendChild(panel.getElement(), header.getElement());

		body = new HTML();
		body.setStyleName("panel-body");
		header.setText("Text text text text text text text text text text text text text text text text text text text text text text text text text text text text text");
		DOM.appendChild(panel.getElement(), body.getElement());

		footer = new HTML();
		footer.setStyleName("panel-footer");
		header.setText("Panel Footer GWT");
		DOM.appendChild(panel.getElement(), footer.getElement());

		DOM.appendChild(parent.getElement(), getElement());
	}
}
