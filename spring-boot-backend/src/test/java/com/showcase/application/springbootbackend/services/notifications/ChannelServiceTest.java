package com.showcase.application.springbootbackend.services.notifications;

import com.showcase.application.springbootbackend.models.notifications.Channel;
import com.showcase.application.springbootbackend.repository.notifications.ChannelRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChannelServiceTest {

    @Mock
    private ChannelRepository repository;
    @InjectMocks
    private ChannelService service;

    @Test
    void testFindByNameAndEnabled() {

        String name = "channel";

        Channel channel = Channel.builder()
                .name(name)
                .build();
        channel.setId(1L);

        when(repository.findByNameAndEnabled(name, true)).thenReturn(channel);

        Channel test = service.findByNameAndEnabled(name, true);

        Assertions.assertNotNull(test);
    }

    @Test
    void testSave() {
        String name = "channel";

        Channel channel = Channel.builder()
                .name(name)
                .build();
        channel.setId(1L);

        when(repository.saveAndFlush(Mockito.any(Channel.class))).thenReturn(channel);

        Channel test = service.saveChannel(name);

        Assertions.assertNotNull(test);
    }
}
