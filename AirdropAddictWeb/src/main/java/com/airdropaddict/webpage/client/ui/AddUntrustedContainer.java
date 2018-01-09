package com.airdropaddict.webpage.client.ui;

import com.airdropaddict.webpage.client.common.ClientEventBus;
import com.airdropaddict.webpage.client.common.ShowAirdropsEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;

public class AddUntrustedContainer extends Composite {
	private static AddUntrustedContainerUiBinder uiBinder = GWT.create(AddUntrustedContainerUiBinder.class);

	interface AddUntrustedContainerUiBinder extends UiBinder<Widget, AddUntrustedContainer> {
	}

	@UiField
	MaterialTextBox nameTextField;
	@UiField
	MaterialTextBox urlTextField;
	@UiField
	MaterialTextArea descriptionTextArea;

	public AddUntrustedContainer() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public void clear() {
		nameTextField.clear();
		urlTextField.clear();
		descriptionTextArea.clear();
	}

	@UiHandler("cancleButton")
	void cancle(ClickEvent e) {
		clear();
		ClientEventBus.post(ShowAirdropsEvent.instance);
	}

	@UiHandler("confirmButton")
	void confirm(ClickEvent e) {
		save();
		clear();
		ClientEventBus.post(ShowAirdropsEvent.instance);
	}

	private void save() {
		// TODO Auto-generated method stub
	}
}
