package com.bsuiramt.servetogetherbackend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "announcements")
@Data
@NoArgsConstructor
public class AnnouncementEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String status;
	private String content;
	private Long reward;
	private Date creationDate;
	private Date expirationDate;
	@ManyToOne
	private AdminEntity author;
	@ManyToOne
	private VolunteerGroupEntity volunteerGroup;
}
