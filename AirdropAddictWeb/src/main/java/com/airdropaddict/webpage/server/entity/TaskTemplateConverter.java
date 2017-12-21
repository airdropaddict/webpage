package com.airdropaddict.webpage.server.entity;

import com.airdropaddict.webpage.shared.data.TaskTemplateData;

import java.util.function.Function;

public class TaskTemplateConverter implements Function<TaskTemplateEntity, TaskTemplateData> {
    @Override
    public TaskTemplateData apply(TaskTemplateEntity entity) {
        TaskTemplateData data = new TaskTemplateData();
        data.setName(entity.getName());
        data.setValue(entity.getValue());
        return data;
    }
}
