package com.bsuiramt.servetogetherbackend.config;

import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {
	
	@Bean(name = "googleCredentials")
	public GoogleCredentials googleCredentials() throws IOException {
		try (InputStream credentialsReader = FirebaseConfig.class.getClassLoader()
				.getResourceAsStream("servetogether-firebase-credentials.json")) {
			return GoogleCredentials.fromStream(credentialsReader);
		}
	}
}
