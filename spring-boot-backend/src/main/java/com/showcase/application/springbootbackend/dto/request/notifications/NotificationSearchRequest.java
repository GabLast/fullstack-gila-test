package com.showcase.application.springbootbackend.dto.request.notifications;

import com.showcase.application.springbootbackend.dto.BaseJson;
import lombok.Builder;

import java.util.Date;

@Builder
public record NotificationSearchRequest(Long userId,
                                        Long messageId,
                                        Long channelId,
                                        Boolean seen,
                                        Date start,
                                        Date end,
                                        Boolean enabled,
                                        String messageBody,
                                        Integer limit,
                                        Integer offset,
                                        String sortColumn,
                                        String sortOrder
) implements BaseJson {
}
