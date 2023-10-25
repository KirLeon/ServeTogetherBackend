package com.bsuiramt.servetogetherbackend.controller;

import com.bsuiramt.servetogetherbackend.dto.request.UserRegistrationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "registration")
public class RegistrationController {
	@PostMapping(value = "/")
	public ResponseEntity<Object> registerUser(@RequestBody UserRegistrationRequest registrationRequest) {
		return registrationRequest.password().length() >= 8 ? ResponseEntity.ok("Hello, user") :
				ResponseEntity.badRequest().build();
	}
}
