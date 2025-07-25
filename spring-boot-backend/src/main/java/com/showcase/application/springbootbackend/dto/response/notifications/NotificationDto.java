package com.showcase.application.springbootbackend.dto.response.notifications;

import lombok.Builder;

import java.util.Date;

@Builder
public record NotificationDto(
        Long id,
        String user,
        Long userId,
        String category,
        String channel,
        String message,
        Date dateCreated,
        boolean seen,
        Date dateSeen
) {
}
