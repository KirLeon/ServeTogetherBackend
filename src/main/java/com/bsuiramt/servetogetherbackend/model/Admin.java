package com.bsuiramt.servetogetherbackend.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Admin {
	
	private String username;
	
	private String phoneNumber;
	
	private String password;
}
