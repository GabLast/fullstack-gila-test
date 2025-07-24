package com.showcase.application.springbootbackend.models.notifications;

import com.showcase.application.springbootbackend.models.BaseModel;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@ToString
public class Category extends BaseModel {

    private String name;

    @Getter
    @AllArgsConstructor
    public enum CategoryType {
        SPORTS( "Sports"),
        FINANCE( "Finance"),
        MOVIES( "Movies");
        private final String name;
    }
}
