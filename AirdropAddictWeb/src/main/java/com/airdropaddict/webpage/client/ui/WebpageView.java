package com.airdropaddict.webpage.client.ui;

import com.airdropaddict.webpage.client.common.ClientEventBus;
import com.airdropaddict.webpage.client.common.ShowAirdropsEvent;
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
import gwt.material.design.client.ui.MaterialPanel;

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
	MaterialLink accountLink;

	@UiField
	MaterialLink airdropsItem;
	@UiField
	MaterialLink untrustedItem;
	@UiField
	MaterialLink tutorialItem;

	@UiField
	MaterialLink sidenavActivator;
	@UiField
	MaterialPanel sideNav;
	@UiField
	MaterialPanel userNav;

	@UiField
	MaterialColumn content;

	@UiField
	AirdropsContainer airdrops;
	UntrustedAirdropsContainer untrusted;
	UntrustedAirdropsContainer tutorial;
	AirdropPreview preview;
	AddUntrustedContainer addUntrusted;

	Widget currentWidget;

	public WebpageView() {
		initWidget(uiBinder.createAndBindUi(this));
		currentWidget = airdrops;
		untrusted = new UntrustedAirdropsContainer();
		tutorial = new UntrustedAirdropsContainer();
		preview = new AirdropPreview();
		addUntrusted = new AddUntrustedContainer();
		ClientEventBus.register(ShowPreviewEvent.class, this::handleShowPreview);
		ClientEventBus.register(ShowAirdropsEvent.class, this::handleShowAirdrops);
	}

	private void changeContent(Widget widget) {
		if (widget != currentWidget) {
			currentWidget.removeFromParent();
			content.add(widget);
			currentWidget = widget;
		}

		sideNav.setVisible(false);
		userNav.setVisible(false);
	}

	@UiHandler({ "airdropsTab", "airdropsItem" })
	void handleAirdropsSelection(ClickEvent e) {
		airdropsTab.setTextColor(Color.ORANGE_LIGHTEN_3);
		untrustedTab.setTextColor(Color.WHITE);
		tutorialTab.setTextColor(Color.WHITE);

		airdropsItem.setTextColor(Color.ORANGE_LIGHTEN_3);
		untrustedItem.setTextColor(Color.WHITE);
		tutorialItem.setTextColor(Color.WHITE);

		changeContent(airdrops);
	}

	@UiHandler({ "untrustedTab", "untrustedItem" })
	void handleUntrustedSelection(ClickEvent e) {
		airdropsTab.setTextColor(Color.WHITE);
		untrustedTab.setTextColor(Color.ORANGE_LIGHTEN_3);
		tutorialTab.setTextColor(Color.WHITE);

		airdropsItem.setTextColor(Color.WHITE);
		untrustedItem.setTextColor(Color.ORANGE_LIGHTEN_3);
		tutorialItem.setTextColor(Color.WHITE);

		changeContent(untrusted);
	}

	@UiHandler({ "tutorialTab", "tutorialItem" })
	void handleTutorialSelection(ClickEvent e) {
		airdropsTab.setTextColor(Color.WHITE);
		untrustedTab.setTextColor(Color.WHITE);
		tutorialTab.setTextColor(Color.ORANGE_LIGHTEN_3);

		airdropsItem.setTextColor(Color.WHITE);
		untrustedItem.setTextColor(Color.WHITE);
		tutorialItem.setTextColor(Color.ORANGE_LIGHTEN_3);

		changeContent(tutorial);
	}

	@UiHandler({ "addUntrustedLink", "addUntrustedSmallLink" })
	void handleAddUntrusted(ClickEvent e) {
		airdropsTab.setTextColor(Color.WHITE);
		untrustedTab.setTextColor(Color.WHITE);
		tutorialTab.setTextColor(Color.WHITE);

		airdropsItem.setTextColor(Color.WHITE);
		untrustedItem.setTextColor(Color.WHITE);
		tutorialItem.setTextColor(Color.WHITE);

		changeContent(addUntrusted);
	}

	void handleShowPreview(ShowPreviewEvent event) {
		preview.present(event.title);
		changeContent(preview);
	}

	void handleShowAirdrops(ShowAirdropsEvent event) {
		handleAirdropsSelection(null);
	}

	@UiHandler("sidenavActivator")
	void handleSideNav(ClickEvent e) {
		sideNav.setVisible(!sideNav.isVisible());
	}

	@UiHandler("accountLink")
	void handleUserNav(ClickEvent e) {
		userNav.setVisible(!userNav.isVisible());
	}
}
