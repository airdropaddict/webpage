package com.airdropaddict.webpage.server.entity;

import com.airdropaddict.webpage.shared.data.AccessData;
import com.airdropaddict.webpage.shared.data.EventData;

import java.util.function.Function;

public class EventConverter implements Function<EventEntity, EventData> {
    private AccessData access;

    public EventConverter(AccessData access) {
        this.access = access;
    }

    @Override
    public EventData apply(EventEntity entity) {
        EventData data = new EventData();
        data.setId(entity.getId());
        data.setName(entity.getName());
        data.setEventType(new CatalogConverter().apply(entity.getEventType().get()));
        data.setUrl(entity.getUrl());
        data.setImageUrl(entity.getImageUrl());
        data.setDescription(entity.getDescription());
        data.setStartTimestamp(entity.getStartTimestamp());
        data.setEndTimestamp(entity.getEndTimestamp());
        data.setTasks(entity.getTasks());
        data.setRating(entity.getRating());
        data.setRateStatus(entity.calculateRateInfo(access));
        return data;
    }
}
