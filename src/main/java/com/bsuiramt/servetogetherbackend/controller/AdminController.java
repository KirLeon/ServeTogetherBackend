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

import java.util.List;

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
					.status(HttpStatus.NOT_FOUND)
					.header("error", "User not found")
					.build();
		}
	}
	
	@GetMapping("/announcement")
	public ResponseEntity<AnnouncementDTO> getAnnouncement(@RequestParam Long id) {
		
		return announcementService.getAnnouncement(id)
				.map(ResponseEntity::ok)
				.orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@GetMapping("/announcement/title")
	public ResponseEntity<List<AnnouncementDTO>> getAnnouncementsByTitle(@RequestParam String title) {
		return ResponseEntity.ok(announcementService.getAllAnnouncementsByTitle(title));
	}
	
	@GetMapping("/announcement/all")
	public ResponseEntity<List<AnnouncementDTO>> getAllAnnouncements() {
		return ResponseEntity.ok(announcementService.getAllAnnouncements());
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
}
