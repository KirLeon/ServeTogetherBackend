package com.bsuiramt.servetogetherbackend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfo {
	private Long id;
	private UserRole role;
	private String username;
	private String password;
	private String phoneNumber;
}
