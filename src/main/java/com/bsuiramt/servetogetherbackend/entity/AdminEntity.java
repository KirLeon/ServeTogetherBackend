package com.bsuiramt.servetogetherbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "administrators")
@NoArgsConstructor
public class AdminEntity {
	
	@Id
	private String username;
	
	private String password;
	
	private String phoneNumber;
}
