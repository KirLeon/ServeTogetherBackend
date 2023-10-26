package com.bsuiramt.servetogetherbackend.repository;

import com.bsuiramt.servetogetherbackend.entity.VolunteerEntity;
import com.bsuiramt.servetogetherbackend.entity.VolunteerGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VolunteerRepository extends JpaRepository<VolunteerEntity, String> {
	Optional<VolunteerEntity> findVolunteerEntityByInfoId(Long infoId);
	
	List<VolunteerEntity> findAllByGroup(VolunteerGroupEntity groupEntity);
	
	int countAllByGroup(VolunteerGroupEntity groupEntity);
}
