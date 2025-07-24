package com.showcase.application.springbootbackend.repository.notifications;

import com.showcase.application.springbootbackend.models.notifications.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @EntityGraph(value = "ReceivedNotification.fetchAll", type = EntityGraph.EntityGraphType.FETCH)
    @Query("select " +
            "data " +
            "from Notification as data " +
            "where (:enabled is null or data.enabled = :enabled) " +
            "and (:seen is null or data.seen = :seen) " +
            "and (:userId is null or data.user.id = :userId) " +
            "and (:messageId is null or data.message.id = :messageId) " +
            "and (:messageBody is null or data.message.message like '' or lower(data.message.message) like trim(lower(concat('%', :messageBody,'%')))) " +
            "and (:channelId is null or data.channel.id = :channelId) " +
            "and (:start is null or data.dateSeen >= :start) " +
            "and (:end is null or data.dateSeen <= :end) "
    )
    List<Notification> findAllFilter(@Param("enabled") Boolean enabled,
                                     @Param("userId") Long userId,
                                     @Param("messageId") Long messageId,
                                     @Param("channelId") Long channelId,
                                     @Param("seen") Boolean seen,
                                     @Param("messageBody") String messageBody,
                                     @Param("start") Date start,
                                     @Param("end") Date end,
                                     Pageable pageable);

    @Query("select " +
            "count(data) " +
            "from Notification as data " +
            "where (:enabled is null or data.enabled = :enabled) " +
            "and (:seen is null or data.seen = :seen) " +
            "and (:userId is null or data.user.id = :userId) " +
            "and (:messageId is null or data.message.id = :messageId) " +
            "and (:messageBody is null or data.message.message like '' or lower(data.message.message) like trim(lower(concat('%', :messageBody,'%')))) " +
            "and (:channelId is null or data.channel.id = :channelId) " +
            "and (:start is null or data.dateSeen >= :start) " +
            "and (:end is null or data.dateSeen <= :end) "
    )
    Integer countFilter(@Param("enabled") Boolean enabled,
                        @Param("userId") Long userId,
                        @Param("messageId") Long messageId,
                        @Param("channelId") Long channelId,
                        @Param("seen") Boolean seen,
                        @Param("messageBody") String messageBody,
                        @Param("start") Date start,
                        @Param("end") Date end);

    @EntityGraph(value = "ReceivedNotification.fetchAll", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Notification> getNotificationById(Long id);
}
