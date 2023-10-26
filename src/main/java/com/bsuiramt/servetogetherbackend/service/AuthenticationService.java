package com.bsuiramt.servetogetherbackend.service;

import com.bsuiramt.servetogetherbackend.exception.InvalidTokenException;
import com.bsuiramt.servetogetherbackend.exception.UserNotFoundException;
import com.bsuiramt.servetogetherbackend.repository.AccountInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final JwtService jwtService;
	private final AccountInfoRepository accountRepository;
	
	@Transactional
	public void authenticateUser(String token) throws InvalidTokenException, UserNotFoundException {
		if (jwtService.isTokenExpired(token)) {
			throw new InvalidTokenException();
		}
		
		String username = jwtService.getUsernameFromToken(token);
		if (!accountRepository.existsAccountInfoEntityByUsername(username)) throw new UserNotFoundException();
	}
}
