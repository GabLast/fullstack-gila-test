package com.showcase.application.springbootbackend.repository.notifications;

import com.showcase.application.springbootbackend.models.notifications.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {

    @Query("select " +
            "data " +
            "from Channel data " +
            "where data.enabled = :enabled " +
            "and trim(lower(data.name)) like trim(lower(:name))"
    )
    Channel findByNameAndEnabled(@Param("name") String name,
                                 @Param("enabled") boolean enabled);
}
