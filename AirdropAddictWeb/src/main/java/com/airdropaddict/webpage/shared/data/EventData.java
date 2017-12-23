package com.airdropaddict.webpage.shared.data;

import java.util.HashMap;
import java.util.Map;

public class EventData extends SimpleEventData {
	private Map<String, String> tasks = new HashMap<>();
	private AccessRateInfoData rateStatus;
	private long numberOfRates;

	public Map<String, String> getTasks() {
		return tasks;
	}

	public void setTasks(Map<String, String> tasks) {
		this.tasks = tasks;
	}

	public AccessRateInfoData getRateStatus() {
		return rateStatus;
	}

	public void setRateStatus(AccessRateInfoData rateStatus) {
		this.rateStatus = rateStatus;
	}

	public long getNumberOfRates() {
		return numberOfRates;
	}

	public void setNumberOfRates(long numberOfRates) {
		this.numberOfRates = numberOfRates;
	}
}
