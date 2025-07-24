package com.showcase.application.springbootbackend.services.notifications;

import com.showcase.application.springbootbackend.models.notifications.Channel;
import com.showcase.application.springbootbackend.models.notifications.UserChannel;
import com.showcase.application.springbootbackend.models.security.User;
import com.showcase.application.springbootbackend.repository.notifications.UserChannelRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserChannelServiceTest {

    @Mock
    private UserChannelRepository repository;
    @InjectMocks
    private UserChannelService service;

    @Test
    void testFindAllByEnabled() {
        User user = User.builder().name("user 2").phoneNumber("222-222-1234").email("mail2@mail.com").build();

        List<UserChannel> list = new ArrayList<>();
        list.add(UserChannel.builder()
                .user(user)
                .channel(Channel.builder().name("channel 1").build())
                .build());
        list.add(UserChannel.builder()
                .user(user)
                .channel(Channel.builder().name("channel 2").build())
                .build());

        when(repository.findAllByUserAndEnabled(user, true)).thenReturn(list);

        List<UserChannel> test = service.findAllByUserAndEnabled(user, true);

        Assertions.assertNotNull(test);
    }
}
