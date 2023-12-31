package com.bsuiramt.servetogetherbackend.controller;

import com.bsuiramt.servetogetherbackend.dto.request.CreateAnnouncementRequest;
import com.bsuiramt.servetogetherbackend.dto.response.AnnouncementDTO;
import com.bsuiramt.servetogetherbackend.exception.AttemptToDeleteAnnouncementInProgressException;
import com.bsuiramt.servetogetherbackend.exception.UserNotFoundException;
import com.bsuiramt.servetogetherbackend.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
	
	private final AnnouncementService announcementService;
	
	@PostMapping("/announcement")
	public ResponseEntity<AnnouncementDTO> addAnnouncement(@RequestBody CreateAnnouncementRequest announcementRequest,
	                                                       @RequestHeader("authToken") String token) {
		
		try {
			return ResponseEntity.ok(announcementService.addAnnouncement(announcementRequest, token));
		} catch (UserNotFoundException e) {
			return ResponseEntity
					.status(HttpStatus.UNAUTHORIZED)
					.header("error", "User not found")
					.build();
		}
	}
	
	@DeleteMapping("/announcement")
	public ResponseEntity<AnnouncementDTO> deleteAnnouncement(@RequestParam Long id) {
		try {
			return announcementService.deleteAnnouncement(id)
					.map(ResponseEntity::ok)
					.orElseGet(() -> ResponseEntity.notFound().build());
		} catch (AttemptToDeleteAnnouncementInProgressException e) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.header("error", "this announcement is currently in progress")
					.build();
		}
	}
	
	@PostMapping("/announcement/confirm")
	public ResponseEntity<AnnouncementDTO> confirmAnnouncementAccomplishment(
			@RequestParam Long id, @RequestParam Integer rating, @RequestHeader("authToken") String token) {
		
		try {
			announcementService.confirmAnnouncementAccomplishment(token, id, rating);
			return ResponseEntity.ok().build();
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
	}
}
