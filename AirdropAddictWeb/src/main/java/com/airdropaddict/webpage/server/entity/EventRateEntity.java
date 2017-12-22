package com.airdropaddict.webpage.server.entity;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Index;

public class EventRateEntity extends BasicEntity {
    @Index
    private Ref<EventEntity> event;
    private int rating;
    @Index
    private String ip;
    @Index
    private Ref<UserEntity> user;

    public Ref<EventEntity> getEvent() {
        return event;
    }

    public void setEvent(Ref<EventEntity> event) {
        this.event = event;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Ref<UserEntity> getUser() {
        return user;
    }

    public void setUser(Ref<UserEntity> user) {
        this.user = user;
    }
}
