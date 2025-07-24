package com.showcase.application.springbootbackend.dto.response;

import lombok.Builder;

@Builder
public record BadResponse(String message,
                          String path,
                          int status) {
}
