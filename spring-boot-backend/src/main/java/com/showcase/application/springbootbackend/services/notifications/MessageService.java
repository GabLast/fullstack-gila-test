package com.showcase.application.springbootbackend.services.notifications;

import com.showcase.application.springbootbackend.dto.response.notifications.MessageDto;
import com.showcase.application.springbootbackend.exceptions.ResourceNotFoundException;
import com.showcase.application.springbootbackend.models.notifications.Message;
import com.showcase.application.springbootbackend.repository.notifications.MessageRepository;
import com.showcase.application.springbootbackend.services.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService extends BaseService<Message, Long> {

    private final MessageRepository repository;

    @Override
    protected JpaRepository<Message, Long> getRepository() {
        return repository;
    }

    @Transactional(readOnly = true)
    public List<MessageDto> findAllByEnabled(boolean enabled) {
        return repository.findAllByEnabled(enabled).stream()
                .map(it->
                        MessageDto.builder()
                                .id(it.getId())
                                .message(it.getMessage())
                                .category(it.getCategory().getName())
                                .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Message getMessageById(Long id) {
        return repository.getMessageById(id).orElseThrow(() -> new ResourceNotFoundException("Message not found"));
    }
}
