package com.showcase.application.springbootbackend.repository.notifications;

import com.showcase.application.springbootbackend.models.notifications.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select " +
            "data " +
            "from Category data " +
            "where data.enabled = :enabled " +
            "and trim(lower(data.name)) like trim(lower(:name))"
    )
    Category findByNameAndEnabled(@Param("name") String name,
                                  @Param("enabled") boolean enabled);
}
