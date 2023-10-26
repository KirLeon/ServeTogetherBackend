package com.bsuiramt.servetogetherbackend.controller;

import com.bsuiramt.servetogetherbackend.dto.request.InviteKeyRequest;
import com.bsuiramt.servetogetherbackend.dto.request.UserRegistrationRequest;
import com.bsuiramt.servetogetherbackend.exception.InvalidInviteKeyException;
import com.bsuiramt.servetogetherbackend.exception.InvalidUserRoleException;
import com.bsuiramt.servetogetherbackend.exception.PhoneNumberIsAlreadyExists;
import com.bsuiramt.servetogetherbackend.exception.UsernameIsAlreadyTakenException;
import com.bsuiramt.servetogetherbackend.model.UserRole;
import com.bsuiramt.servetogetherbackend.model.Volunteer;
import com.bsuiramt.servetogetherbackend.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/registration")
@RequiredArgsConstructor
public class RegistrationController {
	
	private final RegistrationService registrationService;
	
	@PostMapping(value = "/key")
	public ResponseEntity<UserRole> checkInviteKey(@RequestBody InviteKeyRequest keyRequest) {
		try {
			return ResponseEntity.ok(registrationService.checkInviteKey(keyRequest.inviteKey()));
		} catch (InvalidInviteKeyException invalidInviteKeyException) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping(value = "/user")
	public ResponseEntity<Volunteer> registerUser(@RequestBody UserRegistrationRequest registrationRequest) {
		try {
			return ResponseEntity.ok(registrationService.registerUser(registrationRequest));
		} catch (InvalidInviteKeyException invalidInviteKeyException) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.header("error", "The invite code is invalid or expired").build();
		} catch (UsernameIsAlreadyTakenException usernameIsAlreadyTakenException) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.header("error", "This username is already taken").build();
		} catch (PhoneNumberIsAlreadyExists phoneNumberIsAlreadyTakenException) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.header("error", "This phone number is already taken").build();
		} catch (InvalidUserRoleException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.header("error", "Something went wrong during user registration").build();
		}
	}
}
