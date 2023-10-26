package com.bsuiramt.servetogetherbackend.controller;

import com.bsuiramt.servetogetherbackend.exception.*;
import com.bsuiramt.servetogetherbackend.model.VolunteerGroup;
import com.bsuiramt.servetogetherbackend.service.AuthorizationService;
import com.bsuiramt.servetogetherbackend.service.VolunteeringService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/groups")
@RequiredArgsConstructor
public class VolunteerGroupController {
	
	private final VolunteeringService volunteeringService;
	private final AuthorizationService authorizationService;
	
	@GetMapping
	public ResponseEntity<List<VolunteerGroup>> getGroups(@RequestParam String groupName) {
		return ResponseEntity.ok(volunteeringService.getGroupsByName(groupName));
	}
	
	@PostMapping("/new")
	public ResponseEntity<?> addGroup(@RequestParam String groupName) {
		return volunteeringService.createGroup(groupName) ? ResponseEntity.ok().build() :
				ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.header("error", "Cannot create group with this name")
						.build();
	}
	
	@PostMapping("/join")
	public ResponseEntity<?> joinGroup(@RequestParam String groupName,
	                                   @RequestHeader("authToken") String token) {
		
		String username = authorizationService.getUsername(token);
		try {
			volunteeringService.addVolunteerToGroup(username, groupName);
			return ResponseEntity.ok().build();
		} catch (GroupNotFoundException e) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.header("error", "Group not found")
					.build();
		} catch (UserNotFoundException e) {
			return ResponseEntity
					.status(HttpStatus.UNAUTHORIZED)
					.header("error", "User not found")
					.build();
		} catch (GroupIsFullException e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.header("error", "Group is full")
					.build();
		} catch (UserIsAlreadyGroupMemberException e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.header("error", "User is already group member")
					.build();
		}
	}
	
	@PostMapping("/leave")
	public ResponseEntity<?> leaveGroup(@RequestParam String groupName,
	                                    @RequestHeader("authToken") String token) {
		String username = authorizationService.getUsername(token);
		try {
			volunteeringService.leaveGroup(username, groupName);
			return ResponseEntity.ok().build();
		} catch (UserNotFoundException e) {
			return ResponseEntity
					.status(HttpStatus.UNAUTHORIZED)
					.header("error", "User not found")
					.build();
		} catch (UserIsNotAGroupMemberException e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.header("error", "User is not a group member")
					.build();
		}
	}
}
