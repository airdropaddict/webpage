package com.airdropaddict.webpage.shared.data;

import java.io.Serializable;
import java.util.List;

public class PageData<T extends Serializable> implements Serializable {
	private List<T> items;
	private boolean lastPage;

	public PageData() {
	}

	public PageData(List<T> items, boolean lastPage) {
		this.items = items;
		this.lastPage = lastPage;
	}

	public boolean isLastPage() {
		return lastPage;
	}

	public void setLastPage(boolean lastPage) {
		this.lastPage = lastPage;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}
}
