package com.airdropaddict.webpage.shared.data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class EventData extends EntityData {
    private long id;
    private String name;
    private CatalogData eventType;
    private String url;
    private String imageUrl;
    private String description;
    private Date startTimestamp;
    private Date endTimestamp;
    private Map<String, String> tasks;
    private double rating;
    private RateInfoData rateStatus;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CatalogData getEventType() {
        return eventType;
    }

    public void setEventType(CatalogData eventType) {
        this.eventType = eventType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Date startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public Date getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(Date endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public Map<String, String> getTasks() {
        return tasks;
    }

    public void setTasks(Map<String, String> tasks) {
        this.tasks = tasks;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public RateInfoData getRateStatus() {
        return rateStatus;
    }

    public void setRateStatus(RateInfoData rateStatus) {
        this.rateStatus = rateStatus;
    }
}
