package com.bsuiramt.servetogetherbackend.entity;

import jakarta.persistence.Column;
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
	
	@Column(name = "active_announcements_quantity")
	private Long activeAnnQuantity;
	
	@Column(name = "pending_announcements_quantity")
	private Long pendingAnnQuantity;
}
