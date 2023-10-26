package com.bsuiramt.servetogetherbackend.service;

import com.bsuiramt.servetogetherbackend.dto.request.UserAuthenticationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final JwtService jwtService;
	
	//TODO implement correct validation
	public Optional<String> authenticateUser(UserAuthenticationRequest authenticationRequest, String token){
		if (jwtService.validateToken(token, authenticationRequest.username())){
			return Optional.empty();
		} else return Optional.of(jwtService.generateToken(authenticationRequest.username()));
	}
}
