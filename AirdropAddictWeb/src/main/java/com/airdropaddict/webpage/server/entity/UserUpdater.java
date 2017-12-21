package com.airdropaddict.webpage.server.entity;

import com.airdropaddict.webpage.shared.data.UserData;

import java.util.function.BiConsumer;

public class UserUpdater implements BiConsumer<UserEntity, UserData> {
@Override
public void accept(UserEntity entity, UserData data) {
        entity.setEmail(data.getEmail());
        }
}
