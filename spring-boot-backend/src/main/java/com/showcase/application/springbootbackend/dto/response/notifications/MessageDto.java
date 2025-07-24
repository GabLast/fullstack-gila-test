package com.showcase.application.springbootbackend.dto.response.notifications;

import lombok.Builder;

@Builder
public record MessageDto(Long id, String category, String message) {
}
