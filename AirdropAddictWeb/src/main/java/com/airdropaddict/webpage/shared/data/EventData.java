package com.airdropaddict.webpage.shared.data;

import java.io.Serializable;
import java.util.Date;

public class EventData implements Serializable {
    private long id;
    private String name;
    private CatalogData eventType;
    private String description;
    private Date startTimestamp;
    private Date endTimestamp;

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
}
