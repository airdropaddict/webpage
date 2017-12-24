package com.airdropaddict.webpage.server.entity;

import com.airdropaddict.webpage.server.BaseDao;
import com.airdropaddict.webpage.shared.data.AccessData;
import com.airdropaddict.webpage.shared.data.AccessRateInfoData;
import com.airdropaddict.webpage.shared.data.CatalogType;
import com.airdropaddict.webpage.shared.data.EventData;

import java.util.function.Function;

public class EventConverter implements Function<EventEntity, EventData> {
    private AccessData access;

    public EventConverter(AccessData access) {
        this.access = access;
    }

    @Override
    public EventData apply(EventEntity entity) {
        EventData data = new EventData();
        data.setId(entity.getId());
        data.setName(entity.getName());
        data.setEventType(new CatalogConverter().apply(entity.getEventType().get()));
        data.setUrl(entity.getUrl());
        data.setImageUrl(entity.getImageUrl());
        data.setDescription(entity.getDescription());
        data.setInsertTimestamp(entity.getInsertTimestamp());
        data.setStartTimestamp(entity.getStartTimestamp());
        data.setEndTimestamp(entity.getEndTimestamp());
        data.setTasks(entity.getTasks());
        data.setRating(entity.getRating());
        data.setScam(entity.isScam());
        data.setRateStatus(fetchRateStatus(entity));
        data.setNumberOfRates(entity.getNumberOfRates());
        return data;
    }

    private AccessRateInfoData fetchRateStatus(EventEntity entity) {
        UserEntity user = null;
        if (access.getUser() != null && access.getUser().getId() != 0) {
            user = BaseDao.getInstance().load(UserEntity.class, access.getUser().getId());
        }
        CatalogEntity voteType = BaseDao.getInstance().getCatalogByTypeAndCode(CatalogType.EVENT_VOTE_TYPE, "RAT");
        EventVoteEntity vote = BaseDao.getInstance().findUserVotes(entity, voteType, user, access.getIp()).stream().findFirst().orElse(null);
        AccessRateInfoData rateInfo = new AccessRateInfoData();
        if (vote != null) {
            rateInfo.setIp(vote.getIp());
            rateInfo.setRating(vote.getVote());
            rateInfo.setChangeable(access.getUser() != null);
        }
        else {
            rateInfo.setChangeable(true);
        }
        return rateInfo;
    }
}
