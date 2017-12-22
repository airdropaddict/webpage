package com.airdropaddict.webpage.server.entity;

import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

@Entity
public class EventVoteEntity extends BasicEntity {
    @Index
    private Ref<CatalogEntity> voteType;
    @Index
    private Ref<EventEntity> event;
    private int vote;
    @Index
    private String ip;
    @Index
    private Ref<UserEntity> user;

    public Ref<CatalogEntity> getVoteType() {
        return voteType;
    }

    public void setVoteType(Ref<CatalogEntity> voteType) {
        this.voteType = voteType;
    }

    public Ref<EventEntity> getEvent() {
        return event;
    }

    public void setEvent(Ref<EventEntity> event) {
        this.event = event;
    }

    public int getVote() {
        return vote;
    }

    public void setValue(int vote) {
        this.vote = vote;
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
