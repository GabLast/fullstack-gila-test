package com.showcase.application.springbootbackend.models.notifications;

import com.showcase.application.springbootbackend.models.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
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
public class Message extends BaseModel {

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
    @Column(nullable = false, columnDefinition = "longtext")
    private String message;
}
