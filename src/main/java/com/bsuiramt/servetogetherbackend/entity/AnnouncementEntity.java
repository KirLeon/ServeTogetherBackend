package com.bsuiramt.servetogetherbackend.entity;

import com.bsuiramt.servetogetherbackend.model.AnnouncementStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "announcements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementEntity {
	public AnnouncementEntity(String title, String content, Long reward, AdminEntity owner) {
		this.id = 0L;
		this.title = title;
		this.status = AnnouncementStatus.AVAILABLE;
		this.content = content;
		this.reward = reward;
		this.creationDate = new Date();
		this.expirationDate = new Date(this.creationDate.getTime() + 7 * 24 * 60 * 60 * 1000);
		this.owner = owner;
		this.volunteerGroup = null;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	
	@Enumerated(EnumType.STRING)
	private AnnouncementStatus status;
	
	private String content;
	
	private Long reward;
	
	private Date creationDate;
	
	private Date expirationDate;
	
	@ManyToOne(targetEntity = AdminEntity.class)
	@JoinColumn(name = "owner")
	private AdminEntity owner;
	
	@ManyToOne
	@JoinColumn(name = "volunteer_groups")
	private VolunteerGroupEntity volunteerGroup;
}
