package com.showcase.application.springbootbackend.services.security;

import com.showcase.application.springbootbackend.models.security.User;
import com.showcase.application.springbootbackend.repository.security.UserRepository;
import com.showcase.application.springbootbackend.services.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService extends BaseService<User, Long> {

    private final UserRepository repository;

    @Override
    protected JpaRepository<User, Long> getRepository() {
        return repository;
    }

    public void boostrap() {
        repository.save(User.builder().name("User 1").email("mail1@mail.com").phoneNumber("111-111-1111").build());
        repository.save(User.builder().name("User 2").email("mail2@mail.com").phoneNumber("222-222-2222").build());
        repository.save(User.builder().name("User 3").email("mail3@mail.com").phoneNumber("333-333-3333").build());
        repository.save(User.builder().name("User 4").email("mail4@mail.com").phoneNumber("444-444-4444").build());
        repository.save(User.builder().name("User 5").email("mail5@mail.com").phoneNumber("555-555-5555").build());
    }

    public User findUserByIdOrMail(Long id, String mail) {
        return repository.findUserByIdOrMail(id, mail);
    }
}
