package com.bsuiramt.servetogetherbackend.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FirebaseService {
	
	public void sendMessage(String token){
		Message messageToSend = Message.builder().setToken(token).putData("Event", "Trigger").build();
	}
}
