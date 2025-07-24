package com.showcase.application.springbootbackend.repository.notifications;

import com.showcase.application.springbootbackend.models.notifications.UserCategory;
import com.showcase.application.springbootbackend.models.security.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {

    @EntityGraph(attributePaths = {"category"})
    List<UserCategory> findAllByUserAndEnabled(User user, boolean enabled);
}
