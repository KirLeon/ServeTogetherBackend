package com.bsuiramt.servetogetherbackend.repository;

import com.bsuiramt.servetogetherbackend.entity.VolunteerGroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolunteerGroupRepository extends JpaRepository<VolunteerGroupEntity, String> {
	List<VolunteerGroupEntity> findVolunteerGroupEntitiesByGroupNameContainsIgnoreCase(String groupName);
	
	int countAllByGroupName(String groupName);
}
