package com.bsuiramt.servetogetherbackend.controller;

import com.bsuiramt.servetogetherbackend.dto.request.UserAuthenticationRequest;
import com.bsuiramt.servetogetherbackend.dto.response.AuthenticatedUserWithToken;
import com.bsuiramt.servetogetherbackend.exception.IncorrectPasswordException;
import com.bsuiramt.servetogetherbackend.exception.InvalidUserRoleException;
import com.bsuiramt.servetogetherbackend.exception.UserNotFoundException;
import com.bsuiramt.servetogetherbackend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/authorize")
@RequiredArgsConstructor
public class AuthorizationController {
	
	private final AuthenticationService authenticationService;
	
	@PostMapping
	public ResponseEntity<AuthenticatedUserWithToken> authorizeUser(@RequestBody UserAuthenticationRequest authorizationRequest) {
		try {
			return ResponseEntity.ok(authenticationService.authenticateUser(authorizationRequest));
		} catch (UserNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (IncorrectPasswordException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("error", "Incorrect password").build();
		} catch (InvalidUserRoleException e) {
			return ResponseEntity.internalServerError().build();
		}
		
	}
}
