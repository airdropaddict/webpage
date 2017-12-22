package com.airdropaddict.webpage.shared.data;

import java.io.Serializable;

public class AccessRateInfoData implements Serializable {
    private int rating;
    private String ip;
    private boolean changeable;

    public AccessRateInfoData() {
    }

    public AccessRateInfoData(int rating, String ip, boolean changeable) {
        this.rating = rating;
        this.ip = ip;
        this.changeable = changeable;
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

    public boolean isChangeable() {
        return changeable;
    }

    public void setChangeable(boolean changeable) {
        this.changeable = changeable;
    }
}
