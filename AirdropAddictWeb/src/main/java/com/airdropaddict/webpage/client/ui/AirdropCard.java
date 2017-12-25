package com.airdropaddict.webpage.client.ui;

import com.airdropaddict.webpage.client.common.ClientEventBus;
import com.airdropaddict.webpage.client.common.ShowPreviewEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialLabel;

public class AirdropCard extends Composite {
	private static AirdropInfoCardUiBinder uiBinder = GWT.create(AirdropInfoCardUiBinder.class);

	interface AirdropInfoCardUiBinder extends UiBinder<Widget, AirdropCard> {
	}

	@UiField
	MaterialCardTitle title;
	@UiField
	MaterialLabel content;

	public AirdropCard(String title) {
		initWidget(uiBinder.createAndBindUi(this));
		setTitle(title);
	}

	public void setTitle(String titlText) {
		title.setText(titlText);
	}

	@UiHandler("title")
	void handleClick(ClickEvent e) {
		ClientEventBus.post(new ShowPreviewEvent(title.getText()));
	}
}
