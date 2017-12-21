package com.airdropaddict.webpage.client.ui;

import static java.util.stream.IntStream.range;

import com.airdropaddict.webpage.client.EventService;
import com.airdropaddict.webpage.client.EventServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialRow;

public class AirdropsContainer extends Composite {
	private static AirdropsContainerUiBinder uiBinder = GWT.create(AirdropsContainerUiBinder.class);

	interface AirdropsContainerUiBinder extends UiBinder<Widget, AirdropsContainer> {
	}

	private final EventServiceAsync eventService = GWT.create(EventService.class);

	@UiField
	MaterialRow content;

	public AirdropsContainer() {
		initWidget(uiBinder.createAndBindUi(this));
		range(0, 12).forEachOrdered(i -> content.add(new AirdropCard("Airdrop " + i)));
	}
}
