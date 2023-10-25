package com.bsuiramt.servetogetherbackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "administrators")
@Data
@NoArgsConstructor
public class AdminEntity {
	
	@Id
	private String username;
	
	private String password;
	
	private String phoneNumber;
}
