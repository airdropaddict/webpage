package com.airdropaddict.webpage.client.ui;

import static java.util.stream.IntStream.range;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialRow;

public class InfosContainer extends Composite {
	private static InfosContainerUiBinder uiBinder = GWT.create(InfosContainerUiBinder.class);

	interface InfosContainerUiBinder extends UiBinder<Widget, InfosContainer> {
	}

	@UiField
	MaterialRow content;

	public InfosContainer() {
		initWidget(uiBinder.createAndBindUi(this));
		range(0, 4).forEachOrdered(i -> content.add(new InfoCard()));
	}
}
