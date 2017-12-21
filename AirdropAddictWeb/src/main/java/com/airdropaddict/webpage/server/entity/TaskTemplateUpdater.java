package com.airdropaddict.webpage.server.entity;

import com.airdropaddict.webpage.shared.data.TaskTemplateData;

import java.util.function.BiConsumer;

public class TaskTemplateUpdater implements BiConsumer<TaskTemplateEntity, TaskTemplateData> {
    @Override
    public void accept(TaskTemplateEntity entity, TaskTemplateData data) {
        entity.setName(data.getName());
        entity.setValue(data.getValue());
    }
}
