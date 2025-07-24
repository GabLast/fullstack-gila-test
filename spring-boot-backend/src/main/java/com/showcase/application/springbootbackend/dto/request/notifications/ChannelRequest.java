package com.showcase.application.springbootbackend.dto.request.notifications;

import com.showcase.application.springbootbackend.dto.BaseJson;
import lombok.Builder;

@Builder
public record ChannelRequest(
        String name)
        implements BaseJson {
}
