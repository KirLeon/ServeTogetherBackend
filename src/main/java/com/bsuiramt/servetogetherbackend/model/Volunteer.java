package com.bsuiramt.servetogetherbackend.model;

import com.bsuiramt.servetogetherbackend.entity.VolunteerGroupEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Volunteer {
	
	private String username;
	
	private String phoneNumber;
	
	private VolunteerGroupEntity group;
	
	private Integer coins;
}
