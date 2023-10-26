package com.bsuiramt.servetogetherbackend.model;

import com.bsuiramt.servetogetherbackend.entity.AdminEntity;
import com.bsuiramt.servetogetherbackend.entity.VolunteerGroupEntity;
import jakarta.persistence.ManyToOne;

import java.util.Date;

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
