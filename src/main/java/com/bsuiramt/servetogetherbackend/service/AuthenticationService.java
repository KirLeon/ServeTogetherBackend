package com.bsuiramt.servetogetherbackend.service;

import com.bsuiramt.servetogetherbackend.dto.request.UserAuthenticationRequest;
import com.bsuiramt.servetogetherbackend.dto.response.AuthenticatedUserWithToken;
import com.bsuiramt.servetogetherbackend.entity.AccountInfoEntity;
import com.bsuiramt.servetogetherbackend.exception.IncorrectPasswordException;
import com.bsuiramt.servetogetherbackend.exception.InvalidTokenException;
import com.bsuiramt.servetogetherbackend.exception.InvalidUserRoleException;
import com.bsuiramt.servetogetherbackend.exception.UserNotFoundException;
import com.bsuiramt.servetogetherbackend.model.UserRole;
import com.bsuiramt.servetogetherbackend.repository.AccountInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final JwtService jwtService;
	private final AccountInfoRepository accountRepository;
	
	public AuthenticatedUserWithToken authenticateUser(UserAuthenticationRequest authenticationRequest)
			throws UserNotFoundException, InvalidUserRoleException, IncorrectPasswordException {
		
		String username = authenticationRequest.username();
		Optional<AccountInfoEntity> foundAccount = accountRepository
				.findAccountInfoEntityByUsername(username);
		
		if (foundAccount.isEmpty()) throw new UserNotFoundException();
		else if (!foundAccount.get().getPassword().equals(authenticationRequest.password()))
			throw new IncorrectPasswordException();
		
		String token = generateToken(username);
		
		try {
			UserRole userRole = foundAccount.get().getRole();
			String phoneNumber = foundAccount.get().getPhoneNumber();
			return new AuthenticatedUserWithToken(username, phoneNumber, userRole, token);
		} catch (IllegalArgumentException invalidRoleException) {
			throw new InvalidUserRoleException();
		}
		
	}
	
	public String generateToken(String username) throws UserNotFoundException {
		if (accountRepository.findAccountInfoEntityByUsername(username).isEmpty()) throw new UserNotFoundException();
		return jwtService.generateToken(username);
	}
	
	public UserRole checkUserRole(String token) throws UserNotFoundException, InvalidUserRoleException, InvalidTokenException {
		if (token == null || jwtService.isTokenExpired(token)) throw new InvalidTokenException();
		String username = jwtService.getUsernameFromToken(token);
		
		try {
			Optional<AccountInfoEntity> foundAccount = accountRepository.findAccountInfoEntityByUsername(username);
			return foundAccount
					.map(AccountInfoEntity::getRole)
					.orElseThrow(UserNotFoundException::new);
		} catch (IllegalArgumentException invalidRoleException) {
			throw new InvalidUserRoleException();
		}
	}
}
