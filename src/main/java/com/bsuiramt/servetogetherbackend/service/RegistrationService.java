package com.bsuiramt.servetogetherbackend.service;

import com.bsuiramt.servetogetherbackend.dto.request.UserRegistrationRequest;
import com.bsuiramt.servetogetherbackend.entity.AccountInfoEntity;
import com.bsuiramt.servetogetherbackend.entity.InviteKeyEntity;
import com.bsuiramt.servetogetherbackend.entity.VolunteerEntity;
import com.bsuiramt.servetogetherbackend.exception.InvalidInviteKeyException;
import com.bsuiramt.servetogetherbackend.exception.UsernameIsAlreadyTakenException;
import com.bsuiramt.servetogetherbackend.mapper.VolunteerMapper;
import com.bsuiramt.servetogetherbackend.model.UserRole;
import com.bsuiramt.servetogetherbackend.model.Volunteer;
import com.bsuiramt.servetogetherbackend.repository.AccountInfoRepository;
import com.bsuiramt.servetogetherbackend.repository.InviteKeyRepository;
import com.bsuiramt.servetogetherbackend.repository.VolunteerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {
	
	private final InviteKeyRepository keyRepository;
	
	private final VolunteerRepository volunteerRepository;
	
	private final AccountInfoRepository accountInfoRepository;
	
	private final VolunteerMapper volunteerMapper;
	
	public UserRole checkInviteKey(String inviteKey) throws InvalidInviteKeyException {
		log.info("required key: " + inviteKey);
		Optional<InviteKeyEntity> foundKey = keyRepository.findById(inviteKey);
		
		if (foundKey.isEmpty() || foundKey.get().isActivated()) {
			throw new InvalidInviteKeyException();
		}
		
		return UserRole.valueOf(foundKey.get().getRole());
	}
	
	@Transactional
	public Volunteer registerUser(UserRegistrationRequest registrationRequest)
			throws UsernameIsAlreadyTakenException, InvalidInviteKeyException {
		
		Optional<InviteKeyEntity> key = keyRepository.findById(registrationRequest.inviteKey());
		if (key.isEmpty() || key.get().isActivated()) throw new InvalidInviteKeyException();
		
		if (accountInfoRepository.existsAccountInfoEntityByUsername(registrationRequest.username()))
			throw new UsernameIsAlreadyTakenException();
		
		AccountInfoEntity accountInfoToSave = new AccountInfoEntity(UserRole.valueOf(key.get().getRole()),
				registrationRequest.username(), registrationRequest.password(), registrationRequest.phoneNumber());
		AccountInfoEntity savedAccountInfo = accountInfoRepository.save(accountInfoToSave);
		
		volunteerRepository.save(new VolunteerEntity(savedAccountInfo, null, 0));
		
		key = keyRepository.findById(registrationRequest.inviteKey());
		if (key.isEmpty() || key.get().isActivated()) throw new InvalidInviteKeyException();
		
		key.get().setActivated(true);
		return volunteerMapper.entityToModel(savedAccountInfo, null, 0);
	}
	
}
