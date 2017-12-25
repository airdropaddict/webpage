package com.airdropaddict.webpage.client.ui;

import com.airdropaddict.webpage.shared.data.UntrustedAirdropData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.table.MaterialDataTable;
import gwt.material.design.client.ui.table.cell.TextColumn;

public class UntrustedAirdropsContainer extends Composite {
	private static UntrustedAirdropsContainerUiBinder uiBinder = GWT.create(UntrustedAirdropsContainerUiBinder.class);

	interface UntrustedAirdropsContainerUiBinder extends UiBinder<Widget, UntrustedAirdropsContainer> {
	}

	@UiField
	MaterialDataTable<UntrustedAirdropData> table;

	public UntrustedAirdropsContainer() {
		initWidget(uiBinder.createAndBindUi(this));

		table.addColumn(NameColumn.instance);
		table.addColumn(UrlColumn.instance);
	}

	private static class NameColumn extends TextColumn<UntrustedAirdropData> {
		private static final NameColumn instance = new NameColumn();

		@Override
		public String getValue(UntrustedAirdropData object) {
			return object.name;
		}
	}

	private static class UrlColumn extends TextColumn<UntrustedAirdropData> {
		private static final UrlColumn instance = new UrlColumn();

		@Override
		public String getValue(UntrustedAirdropData object) {
			return object.name;
		}
	}
}
