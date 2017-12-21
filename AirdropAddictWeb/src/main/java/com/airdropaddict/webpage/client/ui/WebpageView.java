package com.airdropaddict.webpage.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialTabItem;

public class WebpageView extends Composite {
	private static WebpageViewUiBinder uiBinder = GWT.create(WebpageViewUiBinder.class);

	interface WebpageViewUiBinder extends UiBinder<Widget, WebpageView> {
	}

	@UiField
	MaterialTabItem airdropsTab;
	@UiField
	MaterialTabItem untrustedTab;
	@UiField
	MaterialTabItem tutorialTab;

	@UiField
	MaterialColumn content;

	@UiField
	AirdropsContainer airdrops;
	UntrustedAirdropsContainer untrusted;
	UntrustedAirdropsContainer tutorial;

	Widget currentWidget;

	public WebpageView() {
		initWidget(uiBinder.createAndBindUi(this));
		currentWidget = airdrops;
		untrusted = new UntrustedAirdropsContainer();
		tutorial = new UntrustedAirdropsContainer();
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
		changeContent(airdrops);
	}

	@UiHandler("untrustedTab")
	void handleUntrustedSelection(ClickEvent e) {
		changeContent(untrusted);
	}

	@UiHandler("tutorialTab")
	void handleTutorialSelection(ClickEvent e) {
		changeContent(tutorial);
	}
}
