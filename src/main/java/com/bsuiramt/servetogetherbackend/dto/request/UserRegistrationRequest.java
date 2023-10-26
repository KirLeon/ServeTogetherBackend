package com.bsuiramt.servetogetherbackend.dto.request;

public record UserRegistrationRequest(String username, String phoneNumber, String password, String inviteKey) {
}
