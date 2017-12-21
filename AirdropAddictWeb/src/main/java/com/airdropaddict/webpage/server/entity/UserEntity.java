package com.airdropaddict.webpage.server.entity;

import com.airdropaddict.webpage.shared.data.UserData;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

@Entity
public class UserEntity extends BasicEntity {
    @Index
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
