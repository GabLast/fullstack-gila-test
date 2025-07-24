package com.showcase.application.springbootbackend.services.notifications;

import com.showcase.application.springbootbackend.dto.response.notifications.MessageDto;
import com.showcase.application.springbootbackend.exceptions.ResourceNotFoundException;
import com.showcase.application.springbootbackend.models.notifications.Category;
import com.showcase.application.springbootbackend.models.notifications.Message;
import com.showcase.application.springbootbackend.repository.notifications.MessageRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessageServiceTest {

    @Mock
    private MessageRepository repository;
    @InjectMocks
    private MessageService service;

    @Test
    void testFindAllByEnabled() {
        List<Message> list = new ArrayList<>();
        list.add(Message.builder()
                .category(Category.builder().name("category 1").build())
                .message("msg 1")
                .build());
        list.add(Message.builder()
                .category(Category.builder().name("category 2").build())
                .message("msg 2")
                .build());

        when(repository.findAllByEnabled(true)).thenReturn(list);

        List<MessageDto> test = service.findAllByEnabled(true);

        Assertions.assertNotNull(test);
    }

    @Test
    void testGetMessageById() {
        Long id = 1L;
        Message data = Message.builder()
                .message("my message")
                .build();
        data.setId(id);

        when(repository.getMessageById(id)).thenReturn(Optional.of(data));

        Message test = service.getMessageById(id);

        Assertions.assertNotNull(test);
    }

    @Test
    void testGetMessageByIdThrows() {
        Long id = 999999L;
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.getMessageById(id));
    }
}
