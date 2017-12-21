package com.airdropaddict.webpage.server.entity;

import com.googlecode.objectify.annotation.Id;

public abstract class BasicEntity {
    @Id
    Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
