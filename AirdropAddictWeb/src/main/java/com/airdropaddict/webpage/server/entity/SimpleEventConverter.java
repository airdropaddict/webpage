package com.airdropaddict.webpage.server.entity;

import com.airdropaddict.webpage.shared.data.SimpleEventData;

import java.util.function.Function;

public class SimpleEventConverter implements Function<EventEntity, SimpleEventData> {

    @Override
    public SimpleEventData apply(EventEntity entity) {
        SimpleEventData data = new SimpleEventData();
        data.setId(entity.getId());
        data.setName(entity.getName());
        data.setEventType(new CatalogConverter().apply(entity.getEventType().get()));
        data.setUrl(entity.getUrl());
        data.setImageUrl(entity.getImageUrl());
        data.setDescription(entity.getDescription());
        data.setInsertTimestamp(entity.getInsertTimestamp());
        data.setStartTimestamp(entity.getStartTimestamp());
        data.setEndTimestamp(entity.getEndTimestamp());
        data.setRating(entity.getRating());
        data.setScam(entity.isScam());
        return data;
    }
}
