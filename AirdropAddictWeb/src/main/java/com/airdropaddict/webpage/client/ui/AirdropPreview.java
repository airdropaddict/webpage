package com.airdropaddict.webpage.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialCardTitle;

public class AirdropPreview extends Composite {
	private static AirdropPreviewUiBinder uiBinder = GWT.create(AirdropPreviewUiBinder.class);

	interface AirdropPreviewUiBinder extends UiBinder<Widget, AirdropPreview> {
	}

	@UiField
	MaterialCardTitle title;

	public AirdropPreview() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void present(String titleText) {
		title.setText(titleText);
	}
}
