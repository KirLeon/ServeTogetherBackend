package com.bsuiramt.servetogetherbackend.dto.request;

public record UserRegistrationRequest(String inviteCode, String username, String phoneNumber, String password) {
}
