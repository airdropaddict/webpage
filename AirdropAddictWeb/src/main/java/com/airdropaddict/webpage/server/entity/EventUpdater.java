package com.airdropaddict.webpage.server.entity;

import com.airdropaddict.webpage.server.BaseDao;
import com.airdropaddict.webpage.shared.data.CatalogType;
import com.airdropaddict.webpage.shared.data.EventData;
import com.googlecode.objectify.Ref;

import java.util.function.BiConsumer;

public class EventUpdater implements BiConsumer<EventEntity, EventData> {
    @Override
    public void accept(EventEntity entity, EventData data) {
        CatalogEntity eventType = BaseDao.getInstance().getCatalogByTypeAndCode(CatalogType.EVENT_TYPE, data.getEventType().getCode());
        if (data.getId() > 0)
        {
            entity.setId(data.getId());
        }
        entity.setEventType(Ref.create(eventType));
        entity.setName(data.getName());
        entity.setUrl(data.getUrl());
        entity.setImageUrl(data.getImageUrl());
        entity.setDescription(data.getDescription());
        entity.setStartTimestamp(data.getStartTimestamp());
        entity.setEndTimestamp(data.getEndTimestamp());
        entity.setTasks(data.getTasks());
    }
}
