package com.bsuiramt.servetogetherbackend.dto.response;

import com.bsuiramt.servetogetherbackend.model.AnnouncementStatus;

import java.util.Date;

public record AnnouncementViewDTO(Long id, String title, String content, AnnouncementStatus status, Date expirationDate,
                                  Long price, String ownerContactNumber) {
}
