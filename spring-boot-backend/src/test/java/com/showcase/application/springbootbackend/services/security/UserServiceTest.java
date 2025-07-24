package com.showcase.application.springbootbackend.services.security;

import com.showcase.application.springbootbackend.models.security.User;
import com.showcase.application.springbootbackend.repository.security.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;
    @InjectMocks
    private UserService service;

    @Test
    void testFindByNameAndEnabled() {

        Long id = 1L;
        User user = User.builder().name("user 1").phoneNumber("123-123-1234").email("mail1@mail.com").build();
        user.setId(id);

        when(repository.findUserByIdOrMail(id, null)).thenReturn(user);

        User test = service.findUserByIdOrMail(id, null);

        Assertions.assertNotNull(test);
    }
}
