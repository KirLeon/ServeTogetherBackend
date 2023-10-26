package com.bsuiramt.servetogetherbackend.dto.response;

import com.bsuiramt.servetogetherbackend.model.UserRole;

public record AuthenticatedUserWithToken(String username, String phoneNumber, UserRole userRole, String token) {
}
