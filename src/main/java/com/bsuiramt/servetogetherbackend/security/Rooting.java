package com.bsuiramt.servetogetherbackend.security;

import com.bsuiramt.servetogetherbackend.model.UserRole;

public enum Rooting {
	
	REGISTRATION("/api/v1/signup/"), AUTHENTICATION("/api/v1/auth/"),
	AUTHORIZATION("/api/v1/authorization/"), ADMIN("/api/v1/signup"),
	SWAGGER_UI("/swagger-ui"), API_DOCS("/v3/api-docs");
	
	private String url;
	private UserRole userRole;
	
	private Rooting(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}
}
