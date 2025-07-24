package com.showcase.application.springbootbackend.services.notifications;

import com.showcase.application.springbootbackend.models.notifications.UserChannel;
import com.showcase.application.springbootbackend.models.security.User;
import com.showcase.application.springbootbackend.repository.notifications.UserChannelRepository;
import com.showcase.application.springbootbackend.services.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserChannelService extends BaseService<UserChannel, Long> {

    private final UserChannelRepository repository;

    @Override
    protected JpaRepository<UserChannel, Long> getRepository() {
        return repository;
    }

    @Transactional(readOnly = true)
    public List<UserChannel> findAllByUserAndEnabled(User user, boolean enabled) {
        return repository.findAllByUserAndEnabled(user, enabled);
    }
}
