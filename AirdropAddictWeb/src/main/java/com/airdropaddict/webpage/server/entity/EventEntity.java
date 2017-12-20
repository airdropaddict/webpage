package com.airdropaddict.webpage.server.entity;

import com.airdropaddict.webpage.shared.data.AccessData;
import com.airdropaddict.webpage.shared.data.EventData;
import com.airdropaddict.webpage.shared.data.RateInfoData;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Index;

import java.util.*;

@Entity
public class EventEntity extends BasicEntity {
    private String name;
    @Index
    private Ref<CatalogEntity> eventType;
    private String url;
    private String imageUrl;
    private String description;
    @Index
    private Date startTimestamp;
    @Index
    private Date endTimestamp;
    private Map<String, String> tasks = new HashMap<>();
    private float rating;
    private List<RateInfo> rateHistory = new LinkedList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ref<CatalogEntity> getEventType() {
        return eventType;
    }

    public void setEventType(Ref<CatalogEntity> eventType) {
        this.eventType = eventType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Date startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public Date getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(Date endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public Map<String, String> getTasks() {
        return tasks;
    }

    public void setTasks(Map<String, String> tasks) {
        this.tasks = tasks;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<RateInfo> getRateHistory() {
        return rateHistory;
    }

    public void setRateHistory(List<RateInfo> rateHistory) {
        this.rateHistory = rateHistory;
    }

    public EventData toData(AccessData access) {
        EventData eventData = new EventData();
        eventData.setId(id);
        eventData.setName(name);
        eventData.setEventType(this.eventType.get().toData());
        eventData.setUrl(url);
        eventData.setImageUrl(imageUrl);
        eventData.setDescription(description);
        eventData.setStartTimestamp(startTimestamp);
        eventData.setEndTimestamp(endTimestamp);
        eventData.setTasks(tasks);
        eventData.setRating(rating);
        eventData.setRateStatus(calculateRateInfo(access));
        return eventData;
    }

    public boolean canRate(AccessData access) {
        if (access.getUser() != null)
        {
            return true;
        }
        if (access.getIp() != null) {
            RateInfo rateInfo = getRateInfoByIp(access.getIp());
            if (rateInfo != null)
            {
                return false;
            }
        }
        return true;
    }

    private RateInfoData calculateRateInfo(AccessData access) {
        if (access.getUser() != null)
        {
            RateInfo rateInfo = getRateInfoByUserId(access.getUser().getId());
            if (rateInfo != null)
            {
                return new RateInfoData(rateInfo.getRating(), rateInfo.getIp(), true);
            }
        }
        if (access.getIp() != null) {
            RateInfo rateInfo = getRateInfoByIp(access.getIp());
            if (rateInfo != null)
            {
                return new RateInfoData(rateInfo.getRating(), rateInfo.getIp(), access.getUser() != null);
            }
        }
        return null;
    }

    private RateInfo getRateInfoByUserId(long userId) {
        return rateHistory.stream()
                .filter(r -> r.getUser() != null && r.getUser().get().getId() == userId)
                .findFirst()
                .orElse(null);
    }

    private RateInfo getRateInfoByIp(String ip) {
        return rateHistory.stream()
                .filter(r -> ip.equals(r.getIp()))
                .findFirst()
                .orElse(null);
    }
}
