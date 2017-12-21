package com.airdropaddict.webpage.client.ui;

import static java.util.stream.IntStream.range;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialContainer;
import gwt.material.design.client.ui.MaterialRow;

public class WebpageView extends Composite {
	private static WebpageViewUiBinder uiBinder = GWT.create(WebpageViewUiBinder.class);

	interface WebpageViewUiBinder extends UiBinder<Widget, WebpageView> {
	}

	@UiField
	MaterialContainer content;

	public WebpageView() {
		initWidget(uiBinder.createAndBindUi(this));
		MaterialRow row = new MaterialRow();
		content.add(row);
		range(0, 12).forEachOrdered(i -> row.add(new AirdropInfoCard("Airdrop " + i)));
	}
}