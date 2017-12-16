package com.airdropaddict.webpage.client.ui;

import com.google.gwt.user.client.ui.HTML;

public class AirdropPanel extends HTML {
	private HTML panel;
	private HTML header;
	private HTML body;
	private HTML footer;
	
	public AirdropPanel() {
		super("<div class=\"col-lg-4\"></div>");
		panel = new HTML("<div class=\"panel panel-primary\"></div>");
		header = new HTML("<div class=\"panel-heading\"></div>");
		body = new HTML("<div class=\"panel-body\"></div>");
		footer = new HTML("<div class=\"panel-footer\"></div>");
	}
}
