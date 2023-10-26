package com.bsuiramt.servetogetherbackend.service;

import com.bsuiramt.servetogetherbackend.entity.AnnouncementEntity;
import com.bsuiramt.servetogetherbackend.entity.VolunteerEntity;
import com.bsuiramt.servetogetherbackend.entity.VolunteerGroupEntity;
import com.bsuiramt.servetogetherbackend.exception.AnnouncementIsNotAvailable;
import com.bsuiramt.servetogetherbackend.exception.UserNotFoundException;
import com.bsuiramt.servetogetherbackend.model.AnnouncementStatus;
import com.bsuiramt.servetogetherbackend.repository.AnnouncementRepository;
import com.bsuiramt.servetogetherbackend.repository.VolunteerGroupRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExecutingAnnouncementService {
	
	private final AnnouncementRepository announcementRepository;
	
	private final VolunteerGroupRepository volunteerGroupRepository;
	
	@Lazy
	private final VolunteeringService volunteeringService;
	
	@Transactional
	public boolean takeAnnouncementInWork(String username, Long announcementInd)
			throws AnnouncementIsNotAvailable, UserNotFoundException {
		Optional<AnnouncementEntity> foundAnnouncement = announcementRepository.findById(announcementInd);
		if (foundAnnouncement.isEmpty()) throw new AnnouncementIsNotAvailable();
		
		AnnouncementEntity announcement = foundAnnouncement.get();
		if (!announcement.getStatus().equals(AnnouncementStatus.AVAILABLE)) throw new AnnouncementIsNotAvailable();
		
		VolunteerEntity account = volunteeringService.getAccountByUsername(username);
		VolunteerGroupEntity group = account.getGroup();
		
		if (group.getActiveAnnQuantity() < 3 && group.getPendingAnnQuantity() < 5) {
			group.setActiveAnnQuantity(group.getActiveAnnQuantity() + 1);
			
			announcement.setVolunteerGroup(group);
			announcement.setStatus(AnnouncementStatus.IN_PROGRESS);
			
			return true;
		} else return false;
	}
	
	public boolean finishAnnouncement(String username, Long announcementInd) {
		return true;
	}
}
