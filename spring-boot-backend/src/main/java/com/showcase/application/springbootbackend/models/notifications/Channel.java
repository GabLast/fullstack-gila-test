package com.showcase.application.springbootbackend.models.notifications;

import com.showcase.application.springbootbackend.models.BaseModel;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class Channel extends BaseModel {

    private String name;

    @Getter
    @AllArgsConstructor
    public enum ChannelType {
        SMS( "SMS"),
        EMAIL( "E-mail"),
        PUSH_NOTIFICATION( "Push Notification");

        private final String name;
    }
}
