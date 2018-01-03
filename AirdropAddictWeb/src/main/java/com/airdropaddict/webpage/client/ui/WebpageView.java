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

import gwt.material.design.client.constants.Color;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLink;

public class WebpageView extends Composite {
	private static WebpageViewUiBinder uiBinder = GWT.create(WebpageViewUiBinder.class);

	interface WebpageViewUiBinder extends UiBinder<Widget, WebpageView> {
	}

	@UiField
	MaterialLink airdropsTab;
	@UiField
	MaterialLink untrustedTab;
	@UiField
	MaterialLink tutorialTab;

	@UiField
	MaterialColumn content;

	@UiField
	AirdropsContainer airdrops;
	UntrustedAirdropsContainer untrusted;
	UntrustedAirdropsContainer tutorial;
	AirdropPreview preview;

	Widget currentWidget;

	public WebpageView() {
		initWidget(uiBinder.createAndBindUi(this));
		currentWidget = airdrops;
		untrusted = new UntrustedAirdropsContainer();
		tutorial = new UntrustedAirdropsContainer();
		preview = new AirdropPreview();
		ClientEventBus.register(ShowPreviewEvent.class, this::handleShowPreview);
	}

	private void changeContent(Widget widget) {
		if (widget != currentWidget) {
			currentWidget.removeFromParent();
			content.add(widget);
			currentWidget = widget;
		}
	}

	@UiHandler("airdropsTab")
	void handleAirdropsSelection(ClickEvent e) {
		airdropsTab.setTextColor(Color.ORANGE_LIGHTEN_3);
		untrustedTab.setTextColor(Color.WHITE);
		tutorialTab.setTextColor(Color.WHITE);
		changeContent(airdrops);
	}

	@UiHandler("untrustedTab")
	void handleUntrustedSelection(ClickEvent e) {
		airdropsTab.setTextColor(Color.WHITE);
		untrustedTab.setTextColor(Color.ORANGE_LIGHTEN_3);
		tutorialTab.setTextColor(Color.WHITE);
		changeContent(untrusted);
	}

	@UiHandler("tutorialTab")
	void handleTutorialSelection(ClickEvent e) {
		airdropsTab.setTextColor(Color.WHITE);
		untrustedTab.setTextColor(Color.WHITE);
		tutorialTab.setTextColor(Color.ORANGE_LIGHTEN_3);
		changeContent(tutorial);
	}

	void handleShowPreview(ShowPreviewEvent event) {
		preview.present(event.title);
		changeContent(preview);
	}
}
