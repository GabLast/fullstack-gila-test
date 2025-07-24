package com.showcase.application.springbootbackend.models.notifications;

import com.showcase.application.springbootbackend.models.BaseModel;
import com.showcase.application.springbootbackend.models.security.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@NamedEntityGraph(name = "ReceivedNotification.fetchAll", attributeNodes = {
        @NamedAttributeNode("message")
        , @NamedAttributeNode("user")
        , @NamedAttributeNode("channel")
})
@Table(indexes = {
        @Index(name = "idx_seen", columnList = "seen")
})
public class Notification extends BaseModel {

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Message message;
    @ManyToOne(fetch = FetchType.LAZY)
    private Channel channel;
    private Date dateSeen;
    private boolean seen = false;
}
