package com.bsuiramt.servetogetherbackend.controller;

import com.bsuiramt.servetogetherbackend.dto.request.UserAuthorizationRequest;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "authorize")
public class AuthorizationController {
	@PostMapping
	public ResponseEntity<Object> authorizeUser(@RequestBody UserAuthorizationRequest authorizationRequest) {
		return authorizationRequest.password().length() < 8 ? ResponseEntity.ok("User has been successfully authorized")
				: ResponseEntity.badRequest().build();
	}
}
