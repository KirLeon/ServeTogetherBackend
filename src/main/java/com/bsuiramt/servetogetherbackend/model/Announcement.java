package com.bsuiramt.servetogetherbackend.model;

import com.bsuiramt.servetogetherbackend.entity.AdminEntity;
import com.bsuiramt.servetogetherbackend.entity.VolunteerGroupEntity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Announcement {
	
	private Long id;
	private String title;
	private AnnouncementStatus status;
	private String content;
	private Long reward;
	private Date creationDate;
	private Date expirationDate;
	private Admin owner;
	private VolunteerGroup volunteerGroup;
}
