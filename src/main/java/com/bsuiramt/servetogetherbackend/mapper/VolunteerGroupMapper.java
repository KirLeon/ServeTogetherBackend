package com.bsuiramt.servetogetherbackend.mapper;

import com.bsuiramt.servetogetherbackend.entity.VolunteerGroupEntity;
import com.bsuiramt.servetogetherbackend.model.VolunteerGroup;
import org.springframework.stereotype.Component;

@Component
public class VolunteerGroupMapper {
	public final VolunteerGroup entityToModel(VolunteerGroupEntity entity) {
		return new VolunteerGroup(entity.getGroupName(), entity.getActiveAnnQuantity(), entity.getPendingAnnQuantity());
	}
}
