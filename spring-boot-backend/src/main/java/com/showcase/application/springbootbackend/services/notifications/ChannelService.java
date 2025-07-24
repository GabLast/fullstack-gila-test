package com.showcase.application.springbootbackend.services.notifications;

import com.showcase.application.springbootbackend.exceptions.ResourceExistsException;
import com.showcase.application.springbootbackend.exceptions.ResourceNotFoundException;
import com.showcase.application.springbootbackend.models.notifications.Channel;
import com.showcase.application.springbootbackend.repository.notifications.ChannelRepository;
import com.showcase.application.springbootbackend.services.BaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChannelService extends BaseService<Channel, Long> {

    private final ChannelRepository repository;

    @Override
    protected JpaRepository<Channel, Long> getRepository() {
        return repository;
    }

    public void boostrap() {
        try {
            for (Channel.ChannelType value : Channel.ChannelType.values()) {
                Channel channel = repository.findByNameAndEnabled(value.getName(), true);
                if (channel == null) {
                    save(Channel.builder().name(value.getName()).build());
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public Channel findByNameAndEnabled(String name, boolean enabled) {
        Channel channel = repository.findByNameAndEnabled(name, enabled);

        if(channel == null) {
            throw new ResourceNotFoundException("The channelId name " + name + " was not found.");
        }
        return channel;
    }

    @Transactional
    public Channel saveChannel(String name) {
        Channel channel = repository.findByNameAndEnabled(name, true);
        if(channel != null) {
            throw new ResourceExistsException("The channelId name " + name + " already exists. Please select a new name.");
        }
        return saveAndFlush(Channel.builder().name(name).build());
    }
}
