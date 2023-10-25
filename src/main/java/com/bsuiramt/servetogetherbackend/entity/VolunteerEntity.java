package com.bsuiramt.servetogetherbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "volunteers")
@NoArgsConstructor
public class VolunteerEntity {
	
	@Id
	private String username;
	private String password;
	@Column(unique = true)
	private String phoneNumber;
	private String coins;
	
}
