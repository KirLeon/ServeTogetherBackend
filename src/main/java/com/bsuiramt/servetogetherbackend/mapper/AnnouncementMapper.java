package com.bsuiramt.servetogetherbackend.mapper;

import com.bsuiramt.servetogetherbackend.dto.response.AnnouncementDTO;
import com.bsuiramt.servetogetherbackend.entity.AnnouncementEntity;
import com.bsuiramt.servetogetherbackend.model.Announcement;
import org.springframework.stereotype.Component;

@Component
public class AnnouncementMapper {
	
	public final AnnouncementDTO entityToDTO(AnnouncementEntity entity) {
		return new AnnouncementDTO(entity.getId(), entity.getTitle(), entity.getContent(),
				entity.getStatus(), entity.getExpirationDate(), entity.getReward());
	}
	
}
