package com.bsuiramt.servetogetherbackend.mapper;

import com.bsuiramt.servetogetherbackend.entity.AccountInfoEntity;
import com.bsuiramt.servetogetherbackend.model.AccountInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountInfoMapper {
	AccountInfoMapper INSTANCE = Mappers.getMapper(AccountInfoMapper.class);
	
	public AccountInfoEntity modelToEntity(AccountInfo model);
}
