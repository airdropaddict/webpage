package com.airdropaddict.webpage.server.entity;

import com.airdropaddict.webpage.shared.data.EventData;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.util.Date;

@Entity
public class EventEntity extends BasicEntity {
    private String name;
    @Index
    private Ref<CatalogEntity> eventType;
    private String description;
    @Index
    private Date startTimestamp;
    @Index
    private Date endTimestamp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ref<CatalogEntity> getEventType() {
        return eventType;
    }

    public void setEventType(Ref<CatalogEntity> eventType) {
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

    public EventData toData() {
        EventData eventData = new EventData();
        eventData.setId(id);
        eventData.setName(name);
        eventData.setEventType(this.eventType.get().toData());
        eventData.setDescription(description);
        eventData.setStartTimestamp(startTimestamp);
        eventData.setEndTimestamp(endTimestamp);
        return eventData;
    }
}
