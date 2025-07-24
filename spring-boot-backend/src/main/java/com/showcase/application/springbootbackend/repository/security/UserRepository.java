package com.showcase.application.springbootbackend.repository.security;

import com.showcase.application.springbootbackend.models.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select " +
            "data " +
            "from User data " +
            "where ((:id is not null and data.id = :id) or " +
            "(:mail is not null and trim(lower(data.email)) like trim(lower(:mail))))"
    )
    User findUserByIdOrMail(Long id, String mail);
}
