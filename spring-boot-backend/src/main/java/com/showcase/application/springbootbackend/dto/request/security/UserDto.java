package com.showcase.application.springbootbackend.dto.request.security;


import com.showcase.application.springbootbackend.dto.BaseJson;
import lombok.Builder;

@Builder
public record UserDto(Long id, String email) implements BaseJson {
}
