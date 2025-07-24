package com.showcase.application.springbootbackend.dto.request.notifications;

import com.showcase.application.springbootbackend.dto.BaseJson;
import lombok.Builder;

@Builder
public record MessageRequest(
        String category,
        String message)
        implements BaseJson {
}
