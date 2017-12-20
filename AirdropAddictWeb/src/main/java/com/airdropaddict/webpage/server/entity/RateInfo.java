package com.airdropaddict.webpage.server.entity;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Index;

public class RateInfo {
    private int rating;
    private String ip;
    @Index
    private Ref<UserEntity> user;

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
