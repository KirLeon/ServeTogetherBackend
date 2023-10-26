package com.bsuiramt.servetogetherbackend.controller;

import com.bsuiramt.servetogetherbackend.dto.response.AnnouncementDTO;
import com.bsuiramt.servetogetherbackend.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class ExecutingAnnouncementController {
	
	private final AnnouncementService announcementService;
	
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
	
	@PostMapping("/take")
	public ResponseEntity<?> takeAnnouncementInWork(@RequestParam Long announcementId) {
		//TODO implement logic
		return null;
	}
	
	@PostMapping("/finish")
	public ResponseEntity<?> completeAnnouncement(@RequestParam Long announcementId) {
		//TODO implement logic
		//TODO notify administrator
		return null;
	}
}
