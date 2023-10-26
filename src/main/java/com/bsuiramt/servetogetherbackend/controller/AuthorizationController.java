package com.bsuiramt.servetogetherbackend.controller;

import com.bsuiramt.servetogetherbackend.dto.request.UserAuthenticationRequest;
import com.bsuiramt.servetogetherbackend.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "api/v1/authorize")
@RequiredArgsConstructor
public class AuthorizationController {
	
	private final AuthenticationService authenticationService;
	
	@PostMapping
	public ResponseEntity<String> authorizeUser(@RequestBody UserAuthenticationRequest authorizationRequest,
	                                            @RequestHeader(name = "authToken") String token) {
		Optional<String> updatedToken = authenticationService.authenticateUser(authorizationRequest, token);
		if (updatedToken.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).header("authToken", token).body("Token is up to date");
		} else return ResponseEntity.status(HttpStatus.OK).header("authToken", updatedToken.get()).body("Updated token");
	}
}
