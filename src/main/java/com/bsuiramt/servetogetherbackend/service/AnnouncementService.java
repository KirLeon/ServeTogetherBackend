package com.bsuiramt.servetogetherbackend.service;

import com.bsuiramt.servetogetherbackend.dto.request.CreateAnnouncementRequest;
import com.bsuiramt.servetogetherbackend.dto.response.AnnouncementDTO;
import com.bsuiramt.servetogetherbackend.entity.AccountInfoEntity;
import com.bsuiramt.servetogetherbackend.entity.AdminEntity;
import com.bsuiramt.servetogetherbackend.entity.AnnouncementEntity;
import com.bsuiramt.servetogetherbackend.exception.AttemptToDeleteAnnouncementInProgressException;
import com.bsuiramt.servetogetherbackend.exception.UserNotFoundException;
import com.bsuiramt.servetogetherbackend.mapper.AnnouncementMapper;
import com.bsuiramt.servetogetherbackend.model.AnnouncementStatus;
import com.bsuiramt.servetogetherbackend.repository.AccountInfoRepository;
import com.bsuiramt.servetogetherbackend.repository.AdminRepository;
import com.bsuiramt.servetogetherbackend.repository.AnnouncementRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnnouncementService {
	
	private final AnnouncementRepository announcementRepository;
	
	private final AccountInfoRepository accountRepository;
	
	private final AdminRepository adminRepository;
	
	private final AuthorizationService authorizationService;
	
	private final AnnouncementMapper announcementMapper;
	
	public Optional<AnnouncementDTO> getAnnouncement(Long id) {
		return announcementRepository
				.findById(id).map(announcementMapper::entityToDTO);
	}
	
	public List<AnnouncementDTO> getAllAnnouncements() {
		return announcementRepository
				.findAll()
				.stream()
				.map(announcementMapper::entityToDTO)
				.collect(Collectors.toList());
	}
	
	public List<AnnouncementDTO> getAllAnnouncementsByTitle(String title) {
		return announcementRepository
				.findAll()
				.stream()
				.map(announcementMapper::entityToDTO)
				.collect(Collectors.toList());
	}
	
	@Transactional
	public AnnouncementDTO addAnnouncement(CreateAnnouncementRequest createRequest, String token) throws UserNotFoundException {
		
		String username = authorizationService.getUsername(token);
		
		Optional<AccountInfoEntity> foundAccount = accountRepository.findAccountInfoEntityByUsername(username);
		if (foundAccount.isEmpty()) throw new UserNotFoundException();

//		Optional<AdminEntity> admin = adminRepository.findAdminEntityByInfo(foundAccount.get());
		Optional<AdminEntity> admin = adminRepository.findAdminEntityByInfoId(foundAccount.get().getId());
		if (admin.isEmpty()) throw new UserNotFoundException();
		
		AdminEntity owner = admin.get();
		AnnouncementEntity announcementEntity = new AnnouncementEntity(createRequest.title(), createRequest.content(),
				createRequest.reward(), owner);
		
		AnnouncementEntity savedAnnouncement = announcementRepository.save(announcementEntity);
		return announcementMapper.entityToDTO(savedAnnouncement);
	}
	
	public Optional<AnnouncementDTO> deleteAnnouncement(Long id) throws AttemptToDeleteAnnouncementInProgressException {
		Optional<AnnouncementEntity> foundAnnouncement = announcementRepository.findById(id);
		if (foundAnnouncement.isEmpty()) return Optional.empty();
		else if (foundAnnouncement.get().getStatus().equals(AnnouncementStatus.IN_PROGRESS))
			throw new AttemptToDeleteAnnouncementInProgressException();
		
		announcementRepository.deleteById(id);
		return foundAnnouncement.map(announcementMapper::entityToDTO);
	}
}
