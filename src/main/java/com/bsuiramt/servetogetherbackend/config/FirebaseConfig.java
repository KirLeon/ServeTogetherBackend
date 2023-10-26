package com.bsuiramt.servetogetherbackend.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {
	
	@Bean(name = "googleCredentials")
	public GoogleCredentials googleCredentials() throws IOException {
		try (InputStream credentialsReader = FirebaseConfig.class.getClassLoader()
				.getResourceAsStream("confidential/servetogether-firebase-credentials.json")) {
			return GoogleCredentials.fromStream(credentialsReader);
		}
	}
	
	@Bean
	FirebaseApp firebaseApp(GoogleCredentials credentials) {
		FirebaseOptions options = FirebaseOptions.builder()
				.setCredentials(credentials)
				.build();
		
		return FirebaseApp.initializeApp(options);
	}
	
	@Bean
	FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
		return FirebaseMessaging.getInstance(firebaseApp);
	}
}
