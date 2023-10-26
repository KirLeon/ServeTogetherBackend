package com.bsuiramt.servetogetherbackend.service;

import com.bsuiramt.servetogetherbackend.entity.AnnouncementEntity;
import com.bsuiramt.servetogetherbackend.repository.AnnouncementRepository;
import com.bsuiramt.servetogetherbackend.repository.VolunteerGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExecutingAnnouncementService {
	
	private final AnnouncementRepository announcementRepository;
	
	private final VolunteerGroupRepository volunteerGroupRepository;
	
	public boolean takeAnnouncementInWork(String username, Long announcementInd) {
		Optional<AnnouncementEntity> foundAnnouncement = announcementRepository.findById(announcementInd);
//		if(foundAnnouncement.isEmpty()) throw
		return true;
	}
	
	public boolean finishAnnouncement(String username, Long announcementInd) {
		return true;
	}
}
