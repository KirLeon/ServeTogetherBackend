package com.bsuiramt.servetogetherbackend.service;

import com.bsuiramt.servetogetherbackend.entity.AccountInfoEntity;
import com.bsuiramt.servetogetherbackend.entity.VolunteerEntity;
import com.bsuiramt.servetogetherbackend.entity.VolunteerGroupEntity;
import com.bsuiramt.servetogetherbackend.exception.*;
import com.bsuiramt.servetogetherbackend.mapper.VolunteerGroupMapper;
import com.bsuiramt.servetogetherbackend.model.VolunteerGroup;
import com.bsuiramt.servetogetherbackend.repository.AccountInfoRepository;
import com.bsuiramt.servetogetherbackend.repository.VolunteerGroupRepository;
import com.bsuiramt.servetogetherbackend.repository.VolunteerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VolunteeringService {
	
	private final VolunteerGroupRepository groupRepository;
	private final VolunteerRepository volunteerRepository;
	private final AccountInfoRepository accountRepository;
	private final VolunteerGroupMapper groupMapper = VolunteerGroupMapper.INSTANCE;
	
	public List<VolunteerGroup> getGroupsByName(String name) {
		return groupRepository.findVolunteerGroupEntitiesByGroupNameContainsIgnoreCase(name)
				.stream()
				.map(groupMapper::entityToModel)
				.collect(Collectors.toList());
	}
	
	@Transactional
	public void addVolunteerToGroup(String username, String groupName) throws GroupNotFoundException,
			GroupIsFullException, UserNotFoundException, UserIsAlreadyGroupMemberException {
		
		VolunteerEntity volunteer = getAccountByUsername(username);
		if (volunteer.getGroup() != null) throw new UserIsAlreadyGroupMemberException();
		
		Optional<VolunteerGroupEntity> foundGroup = groupRepository.findById(groupName);
		if (foundGroup.isEmpty()) throw new GroupNotFoundException();
		
		VolunteerGroupEntity group = foundGroup.get();
		int membersQuantity = volunteerRepository.countAllByGroup(group);
		if (membersQuantity > 4) throw new GroupIsFullException();
		
		volunteer.setGroup(group);
	}
	
	@Transactional
	public void leaveGroup(String username, String groupName) throws UserNotFoundException,
			UserIsNotAGroupMember {
		
		VolunteerEntity volunteer = getAccountByUsername(username);
		if (volunteer.getGroup() == null) throw new UserIsNotAGroupMember();
		
		volunteer.setGroup(null);
	}
	
	private VolunteerEntity getAccountByUsername(String username) throws UserNotFoundException {
		Optional<AccountInfoEntity> foundAccount = accountRepository.findAccountInfoEntityByUsername(username);
		if (foundAccount.isEmpty()) throw new UserNotFoundException();
		
		AccountInfoEntity account = foundAccount.get();
		Optional<VolunteerEntity> foundVolunteer = volunteerRepository.findVolunteerEntityByInfoId(account.getId());
		if (foundVolunteer.isEmpty()) throw new UserNotFoundException();
		
		return foundVolunteer.get();
	}
}
