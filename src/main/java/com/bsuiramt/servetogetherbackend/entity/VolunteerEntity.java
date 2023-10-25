package com.bsuiramt.servetogetherbackend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "volunteers")
@Data
@NoArgsConstructor
public class VolunteerEntity {
	
	@Id
	private String username;
	
	private String password;
	
	@Column(unique = true)
	private String phoneNumber;
	
	@JoinColumn(name = "group_name")
	@ManyToOne
	private VolunteerGroupEntity group;
	
	@Min(value = 0, message = "Coin amount should not be less than zero")
	private Long coins;
	
}
