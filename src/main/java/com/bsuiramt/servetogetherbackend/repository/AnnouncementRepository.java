package com.bsuiramt.servetogetherbackend.repository;

import com.bsuiramt.servetogetherbackend.entity.AdminEntity;
import com.bsuiramt.servetogetherbackend.entity.AnnouncementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementRepository extends JpaRepository<AnnouncementEntity, Long> {
	List<AnnouncementEntity> findAllByTitleContainingIgnoreCase(String title);
	List<AnnouncementEntity> findAllByOwner(AdminEntity owner);
}
