package com.showcase.application.springbootbackend.dto.response.notifications;

import lombok.Builder;

import java.util.List;

@Builder
public record NotificationResponse(List<NotificationDto> data) {
}
