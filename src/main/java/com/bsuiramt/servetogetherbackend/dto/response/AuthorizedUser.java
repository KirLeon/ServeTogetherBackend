package com.bsuiramt.servetogetherbackend.dto.response;

import com.bsuiramt.servetogetherbackend.model.UserRole;

public record AuthorizedUser(String username, String phoneNumber, UserRole userRole) {
}
