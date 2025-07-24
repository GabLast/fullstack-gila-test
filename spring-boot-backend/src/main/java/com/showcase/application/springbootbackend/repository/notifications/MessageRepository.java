package com.showcase.application.springbootbackend.repository.notifications;

import com.showcase.application.springbootbackend.models.notifications.Message;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @EntityGraph(attributePaths = {"category"})
    List<Message> findAllByEnabled(boolean enabled);

    @EntityGraph(attributePaths = {"category"})
    Optional<Message> getMessageById(Long id);
}
