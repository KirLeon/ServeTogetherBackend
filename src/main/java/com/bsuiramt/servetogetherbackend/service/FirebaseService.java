package com.bsuiramt.servetogetherbackend.service;

import com.bsuiramt.servetogetherbackend.entity.AccountInfoEntity;
import com.bsuiramt.servetogetherbackend.repository.AccountInfoRepository;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FirebaseService {
	
	private final AccountInfoRepository accountInfoRepository;
	
	public void sendMessage(String token) {
		Message messageToSend = Message.builder().setToken(token).putData("Event", "Trigger").build();
	}
	
	public void notificateAdmin(String username) {
		Optional<AccountInfoEntity> foundAccount =
				accountInfoRepository.findAccountInfoEntityByUsername(username);
		
		if (foundAccount.isPresent()) {
			//TODO implement notification
		}
	}
}
