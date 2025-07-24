package com.showcase.application.springbootbackend.dto.request.notifications;

import com.showcase.application.springbootbackend.dto.request.security.UserDto;
import lombok.Builder;

@Builder
public record CheckNotificationRequest(UserDto userDto, Long notificationId) {
}
