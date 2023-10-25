package com.bsuiramt.servetogetherbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "volunteer_groups")
@Data
@NoArgsConstructor
public class VolunteerGroupEntity {
	
	@Id
	private String groupName;
	
	private Long activeAnnQuantity;
	
	private Long pendingAnnQuantity;
}
