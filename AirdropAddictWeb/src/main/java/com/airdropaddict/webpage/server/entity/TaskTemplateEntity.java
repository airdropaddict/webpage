package com.airdropaddict.webpage.server.entity;

import com.googlecode.objectify.annotation.Entity;

@Entity
public class TaskTemplateEntity extends BasicEntity {
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
