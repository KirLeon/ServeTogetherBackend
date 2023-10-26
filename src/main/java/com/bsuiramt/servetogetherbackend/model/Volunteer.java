package com.bsuiramt.servetogetherbackend.model;

import com.bsuiramt.servetogetherbackend.model.VolunteerGroup;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Volunteer {
	
	private String username;
	
	private String phoneNumber;
	
	private VolunteerGroup group;
	
	private Integer coins;
}
