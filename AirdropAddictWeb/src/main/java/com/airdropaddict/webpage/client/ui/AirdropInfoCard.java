package com.airdropaddict.webpage.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCardTitle;
import gwt.material.design.client.ui.MaterialLabel;

public class AirdropInfoCard extends Composite {
	private static AirdropInfoCardUiBinder uiBinder = GWT.create(AirdropInfoCardUiBinder.class);

	interface AirdropInfoCardUiBinder extends UiBinder<Widget, AirdropInfoCard> {
	}

	@UiField
	MaterialCardTitle title;
	@UiField
	MaterialLabel content;

	public AirdropInfoCard() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
