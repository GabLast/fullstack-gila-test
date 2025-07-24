package com.showcase.application.springbootbackend.services.notifications;

import com.showcase.application.springbootbackend.models.notifications.Category;
import com.showcase.application.springbootbackend.models.notifications.UserCategory;
import com.showcase.application.springbootbackend.models.security.User;
import com.showcase.application.springbootbackend.repository.notifications.UserCategoryRepository;
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
public class UserCategoryServiceTest {

    @Mock
    private UserCategoryRepository repository;
    @InjectMocks
    private UserCategoryService service;

    @Test
    void testFindAllByEnabled() {
        User user = User.builder().name("user 2").phoneNumber("222-222-1234").email("mail2@mail.com").build();

        List<UserCategory> list = new ArrayList<>();
        list.add(UserCategory.builder()
                .user(user)
                .category(Category.builder().name("Category 1").build())
                .build());
        list.add(UserCategory.builder()
                .user(user)
                .category(Category.builder().name("Category 2").build())
                .build());

        when(repository.findAllByUserAndEnabled(user, true)).thenReturn(list);

        List<UserCategory> test = service.findAllByUserAndEnabled(user, true);

        Assertions.assertNotNull(test);
    }
}
