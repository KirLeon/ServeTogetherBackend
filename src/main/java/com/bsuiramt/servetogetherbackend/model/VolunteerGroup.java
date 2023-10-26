package com.bsuiramt.servetogetherbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerGroup {
	
	private String groupName;
	private Long activeAnnQuantity;
	private Long pendingAnnQuantity;
}
