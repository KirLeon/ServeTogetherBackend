package com.bsuiramt.servetogetherbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.Min;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor
public class VolunteerGroupEntity {
	
	@Id
	private String username;
	private String password;
	private String phone_number;
	@Min(value = 0, message = "Price should not be less than zero")
	private Long coins;
}
