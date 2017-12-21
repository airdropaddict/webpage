package com.airdropaddict.webpage.server.entity;

import com.airdropaddict.webpage.shared.data.UserData;

import java.util.function.Function;

public class UserConverter implements Function<UserEntity, UserData> {
    @Override
    public UserData apply(UserEntity entity) {
        UserData data = new UserData();
        data.setId(entity.getId());
        data.setEmail(entity.getEmail());
        return data;
    }
}
