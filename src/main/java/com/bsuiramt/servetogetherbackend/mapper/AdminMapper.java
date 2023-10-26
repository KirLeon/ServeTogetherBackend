package com.bsuiramt.servetogetherbackend.mapper;

import com.bsuiramt.servetogetherbackend.entity.AccountInfoEntity;
import com.bsuiramt.servetogetherbackend.entity.AdminEntity;
import com.bsuiramt.servetogetherbackend.model.Admin;
import org.springframework.stereotype.Component;

@Component
public class AdminMapper {
	
	public final Admin entityToModel(AccountInfoEntity accountInfo) {
		return new Admin(accountInfo.getUsername(), accountInfo.getPhoneNumber(), accountInfo.getPassword());
	}
}
