package com.bsuiramt.servetogetherbackend.mapper;

import com.bsuiramt.servetogetherbackend.entity.AccountInfoEntity;
import com.bsuiramt.servetogetherbackend.entity.VolunteerGroupEntity;
import com.bsuiramt.servetogetherbackend.model.Volunteer;
import org.springframework.stereotype.Component;

@Component
public class VolunteerMapper {
	
	private final VolunteerGroupMapper groupMapper = VolunteerGroupMapper.INSTANCE;
	
	public final Volunteer entityToModel(AccountInfoEntity accountInfo, VolunteerGroupEntity groupEntity, Integer coins) {
		return new Volunteer(accountInfo.getUsername(), accountInfo.getPhoneNumber(), accountInfo.getPassword(), groupMapper.entityToModel(groupEntity), coins);
	}
}
