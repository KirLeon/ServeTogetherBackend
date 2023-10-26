package com.bsuiramt.servetogetherbackend.service;

import com.bsuiramt.servetogetherbackend.entity.AnnouncementEntity;
import com.bsuiramt.servetogetherbackend.entity.VolunteerEntity;
import com.bsuiramt.servetogetherbackend.entity.VolunteerGroupEntity;
import com.bsuiramt.servetogetherbackend.exception.AnnouncementIsNotAvailableException;
import com.bsuiramt.servetogetherbackend.exception.UserNotFoundException;
import com.bsuiramt.servetogetherbackend.model.AnnouncementStatus;
import com.bsuiramt.servetogetherbackend.repository.AnnouncementRepository;
import com.bsuiramt.servetogetherbackend.repository.VolunteerGroupRepository;
import com.bsuiramt.servetogetherbackend.repository.VolunteerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExecutingAnnouncementService {
	
	private final AnnouncementRepository announcementRepository;
	
	private final VolunteerGroupRepository volunteerGroupRepository;
	
	private final VolunteerRepository volunteerRepository;
	
	@Lazy
	private final VolunteeringService volunteeringService;
	
	@Transactional
	public boolean takeAnnouncementInWork(String username, Long announcementId)
			throws AnnouncementIsNotAvailableException, UserNotFoundException {
		
		AnnouncementEntity announcement = getAnnouncement(announcementId);
		VolunteerGroupEntity group = getGroup(username);
		
		if (group.getActiveAnnQuantity() < 3 && group.getPendingAnnQuantity() < 5) {
			group.setActiveAnnQuantity(group.getActiveAnnQuantity() + 1);
			
			announcement.setVolunteerGroup(group);
			announcement.setStatus(AnnouncementStatus.IN_PROGRESS);
			
			return true;
		} else return false;
	}
	
	@Transactional
	public String finishAnnouncement(String username, Long announcementId) throws AnnouncementIsNotAvailableException,
			UserNotFoundException {
		
		AnnouncementEntity announcement = getAnnouncement(announcementId);
		VolunteerGroupEntity group = getGroup(username);
		
		if (!announcement.getVolunteerGroup().equals(group)) throw new AnnouncementIsNotAvailableException();
		
		announcement.setStatus(AnnouncementStatus.PENDING);
		group.setActiveAnnQuantity(group.getActiveAnnQuantity() - 1);
		group.setPendingAnnQuantity(group.getPendingAnnQuantity() + 1);
		
		return announcement.getOwner().getInfo().getUsername();
	}
	
	public AnnouncementEntity getAnnouncement(Long announcementId) throws AnnouncementIsNotAvailableException {
		
		Optional<AnnouncementEntity> foundAnnouncement = announcementRepository.findById(announcementId);
		if (foundAnnouncement.isEmpty() || !foundAnnouncement.get().getStatus().equals(AnnouncementStatus.AVAILABLE))
			throw new AnnouncementIsNotAvailableException();
		
		return foundAnnouncement.get();
	}
	
	public VolunteerGroupEntity getGroup(String username) throws UserNotFoundException {
		VolunteerEntity account = volunteeringService.getAccountByUsername(username);
		return account.getGroup();
	}
	
	public void awardVolunteers(VolunteerGroupEntity group, Long price, Integer rating) {
		Long award = price * (rating * 15L - 40);
		group.setPendingAnnQuantity(group.getPendingAnnQuantity() - 1);
		
		List<VolunteerEntity> volunteers =
				volunteerRepository.findAllByGroup(group);
		volunteers.forEach(volunteer -> volunteer.setCoins((int) (volunteer.getCoins() + award)));
	}
	
}
