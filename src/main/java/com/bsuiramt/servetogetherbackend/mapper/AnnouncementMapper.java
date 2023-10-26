package com.bsuiramt.servetogetherbackend.mapper;

import com.bsuiramt.servetogetherbackend.dto.response.AnnouncementDTO;
import com.bsuiramt.servetogetherbackend.dto.response.AnnouncementViewDTO;
import com.bsuiramt.servetogetherbackend.entity.AnnouncementEntity;
import org.springframework.stereotype.Component;

@Component
public class AnnouncementMapper {
	
	public final AnnouncementDTO entityToDTO(AnnouncementEntity entity) {
		return new AnnouncementDTO(entity.getId(), entity.getTitle(), entity.getContent(),
				entity.getStatus(), entity.getExpirationDate(), entity.getReward());
	}
	
	public final AnnouncementViewDTO entityToView(AnnouncementEntity entity, String ownerContactNumber) {
		return new AnnouncementViewDTO(entity.getId(), entity.getTitle(), entity.getContent(),
				entity.getStatus(), entity.getExpirationDate(), entity.getReward(), ownerContactNumber);
	}
}
