package com.bsuiramt.servetogetherbackend.model;

public enum UserRole {
	VOLUNTEER("volunteer"), ADMIN("admin");
	
	private UserRole(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}
	
	private final String role;
}
