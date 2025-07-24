package com.showcase.application.springbootbackend.services.notifications;

import com.showcase.application.springbootbackend.exceptions.ResourceNotFoundException;
import com.showcase.application.springbootbackend.models.notifications.Category;
import com.showcase.application.springbootbackend.models.notifications.Message;
import com.showcase.application.springbootbackend.models.notifications.Notification;
import com.showcase.application.springbootbackend.models.security.User;
import com.showcase.application.springbootbackend.repository.notifications.NotificationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Mock
    private NotificationRepository repository;
    @InjectMocks
    private NotificationService service;

    @Test
    void testFindAllFilter() {
        List<Notification> list = new ArrayList<>();
        list.add(Notification.builder()
                .user(User.builder().name("user 1").phoneNumber("123-123-1234").email("mail1@mail.com").build())
                .message(Message.builder().category(Category.builder().name("category").build()).message("my message").build())
                .build());
        list.add(Notification.builder()
                .user(User.builder().name("user 2").phoneNumber("222-222-1234").email("mail2@mail.com").build())
                .message(Message.builder().category(Category.builder().name("category 2").build()).message("my message 2").build())
                .build());

        when(repository.findAllFilter(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(Pageable.class))).thenReturn(list);

        List<Notification> test = service.findAllFilter(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), 2, 0, Mockito.any());

        Assertions.assertNotNull(test);
    }

    @Test
    void testCountAllFilter() {
        when(repository.countFilter(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(0);

        Integer test = service.countFilter(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any());

        Assertions.assertNotNull(test);
    }

    @Test
    void testGetNotificationById() {
        Long id = 1L;
        Notification data =Notification.builder()
                .user(User.builder().name("user 1").phoneNumber("123-123-1234").email("mail1@mail.com").build())
                .message(Message.builder().category(Category.builder().name("category").build()).message("my message").build())
                .build();
        data.setId(id);

        when(repository.getNotificationById(id)).thenReturn(Optional.of(data));

        Notification test = service.getNotificationById(id);

        Assertions.assertNotNull(test);
    }

    @Test
    void testGetMessageByIdThrows() {
        Long id = 999999L;
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.getNotificationById(id));
    }
}
