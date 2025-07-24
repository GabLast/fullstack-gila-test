package com.showcase.application.springbootbackend.repository.notifications;

import com.showcase.application.springbootbackend.models.notifications.UserChannel;
import com.showcase.application.springbootbackend.models.security.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserChannelRepository extends JpaRepository<UserChannel, Long> {

    @EntityGraph(attributePaths = {"channel"})
    List<UserChannel> findAllByUserAndEnabled(User user, boolean enabled);
}
