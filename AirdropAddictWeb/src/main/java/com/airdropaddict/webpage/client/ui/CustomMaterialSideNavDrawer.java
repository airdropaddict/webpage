package com.airdropaddict.webpage.client.ui;

import static gwt.material.design.client.js.JsMaterialElement.$;

import gwt.material.design.client.base.MaterialWidget;
import gwt.material.design.client.base.helper.DOMHelper;
import gwt.material.design.client.js.JsMaterialElement;
import gwt.material.design.client.js.JsSideNavOptions;
import gwt.material.design.client.ui.MaterialSideNavDrawer;

public class CustomMaterialSideNavDrawer extends MaterialSideNavDrawer {
	private MaterialWidget nav;

	protected void load(boolean strict) {
		activator = DOMHelper.getElementByAttribute("id", "sidenavActivator");

		setup();

		JsSideNavOptions options = new JsSideNavOptions();
		options.menuWidth = width;
		options.edge = edge != null ? edge.getCssName() : null;
		options.closeOnClick = closeOnClick;

		JsMaterialElement element = $(activator);
		element.sideNav(options);

		element.off("side-nav-closing");
		element.on("side-nav-closing", e1 -> {
			onClosing();
			return true;
		});

		element.off("side-nav-closed");
		element.on("side-nav-closed", e1 -> {
			onClosed();
			return true;
		});

		element.off("side-nav-opening");
		element.on("side-nav-opening", e1 -> {
			onOpening();
			return true;
		});

		element.off("side-nav-opened");
		element.on("side-nav-opened", e1 -> {
			onOpened();
			return true;
		});
	}

	@Override
	protected MaterialWidget getNavMenu() {
		if (nav == null) {
			nav = new MaterialWidget(DOMHelper.getElementByAttribute("id", "header"));
		}
		return nav;
	}
	
	@Override
	public void show() {
		setHeight("600px");
		super.show();
	}
}
