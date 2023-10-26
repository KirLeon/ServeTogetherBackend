package com.bsuiramt.servetogetherbackend.mapper;

import com.bsuiramt.servetogetherbackend.entity.VolunteerGroupEntity;
import com.bsuiramt.servetogetherbackend.model.VolunteerGroup;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface VolunteerGroupMapper {
	VolunteerGroupMapper INSTANCE = Mappers.getMapper(VolunteerGroupMapper.class);
	public VolunteerGroup entityToModel(VolunteerGroupEntity entity);
	public VolunteerGroupEntity modelToEntity(VolunteerGroup model);
}
