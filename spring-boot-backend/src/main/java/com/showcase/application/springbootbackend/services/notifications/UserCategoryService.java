package com.showcase.application.springbootbackend.services.notifications;

import com.showcase.application.springbootbackend.models.notifications.UserCategory;
import com.showcase.application.springbootbackend.models.security.User;
import com.showcase.application.springbootbackend.repository.notifications.UserCategoryRepository;
import com.showcase.application.springbootbackend.services.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserCategoryService extends BaseService<UserCategory, Long> {

    private final UserCategoryRepository repository;

    @Override
    protected JpaRepository<UserCategory, Long> getRepository() {
        return repository;
    }

    public List<UserCategory> findAllByUserAndEnabled(User user, boolean enabled) {
        return repository.findAllByUserAndEnabled(user, enabled);
    }
}
