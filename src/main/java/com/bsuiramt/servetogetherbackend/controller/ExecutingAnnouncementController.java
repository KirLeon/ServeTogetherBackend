package com.bsuiramt.servetogetherbackend.controller;

import com.bsuiramt.servetogetherbackend.dto.response.AnnouncementDTO;
import com.bsuiramt.servetogetherbackend.dto.response.AnnouncementViewDTO;
import com.bsuiramt.servetogetherbackend.exception.AnnouncementIsNotAvailable;
import com.bsuiramt.servetogetherbackend.exception.UserNotFoundException;
import com.bsuiramt.servetogetherbackend.service.AnnouncementService;
import com.bsuiramt.servetogetherbackend.service.AuthorizationService;
import com.bsuiramt.servetogetherbackend.service.ExecutingAnnouncementService;
import com.bsuiramt.servetogetherbackend.service.FirebaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class ExecutingAnnouncementController {
	
	private final AnnouncementService announcementService;
	private final ExecutingAnnouncementService executingAnnouncementService;
	private final AuthorizationService authorizationService;
	private final FirebaseService firebaseService;
	
	
	@GetMapping("/announcement")
	public ResponseEntity<AnnouncementViewDTO> getAnnouncement(@RequestParam Long id) {
		
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
	
	@PostMapping("/take")
	public ResponseEntity<?> takeAnnouncementInWork(@RequestParam Long announcementId,
	                                                @RequestHeader("authToken") String token) {
		
		String username = authorizationService.getUsername(token);
		
		try {
			return executingAnnouncementService.takeAnnouncementInWork(username, announcementId) ?
					ResponseEntity.ok().build() :
					ResponseEntity.status(HttpStatus.BAD_REQUEST)
							.header("error", "Group cannot take another task").build();
		} catch (AnnouncementIsNotAvailable e) {
			return ResponseEntity.notFound().build();
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	@PostMapping("/finish")
	public ResponseEntity<?> completeAnnouncement(@RequestParam Long announcementId,
	                                              @RequestHeader("authToken") String token) {
		
		String username = authorizationService.getUsername(token);
		try {
			String ownerContact = executingAnnouncementService.finishAnnouncement(username, announcementId);
			firebaseService.notificateAdmin(ownerContact);
			
			return ResponseEntity.ok().build();
		} catch (AnnouncementIsNotAvailable e) {
			return ResponseEntity.notFound()
					.header("error", "This event is unavailable for you to finish")
					.build();
		} catch (UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
	}
}
