package com.bsuiramt.servetogetherbackend.controller;

import com.bsuiramt.servetogetherbackend.exception.InvalidTokenException;
import com.bsuiramt.servetogetherbackend.exception.UserNotFoundException;
import com.bsuiramt.servetogetherbackend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	
	@PostMapping
	public ResponseEntity<?> authenticateUser(@RequestHeader("authToken") String token) {
		try {
			authenticationService.authenticateUser(token);
			return ResponseEntity.ok().build();
		} catch (UserNotFoundException | InvalidTokenException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
}
