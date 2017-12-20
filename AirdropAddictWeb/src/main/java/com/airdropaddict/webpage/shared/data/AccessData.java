package com.airdropaddict.webpage.shared.data;

import java.io.Serializable;

public class AccessData implements Serializable {
    private UserData user;
    private String ip;

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
