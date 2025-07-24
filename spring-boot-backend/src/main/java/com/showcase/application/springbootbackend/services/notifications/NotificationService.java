package com.showcase.application.springbootbackend.services.notifications;

import com.showcase.application.springbootbackend.exceptions.ResourceNotFoundException;
import com.showcase.application.springbootbackend.models.notifications.Notification;
import com.showcase.application.springbootbackend.repository.notifications.NotificationRepository;
import com.showcase.application.springbootbackend.services.BaseService;
import com.showcase.application.springbootbackend.utils.OffsetBasedPageRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService extends BaseService<Notification, Long> {

    private final NotificationRepository repository;

    @Override
    protected JpaRepository<Notification, Long> getRepository() {
        return repository;
    }

    @Transactional(readOnly = true)
    public List<Notification> findAllFilter(Long user, Long message, Long channel, Boolean seen, String messageBody,
                                            Date start, Date end,
                                            Boolean enabled, Integer limit, Integer offset, Sort sort) {
        return repository.findAllFilter(enabled, user, message, channel, seen, messageBody, start, end,
                sort == null ? new OffsetBasedPageRequest(limit, offset) : new OffsetBasedPageRequest(limit, offset, sort));
    }

    @Transactional(readOnly = true)
    public Integer countFilter(Long user, Long message, Long channel, Boolean seen, String messageBody,
                               Date start, Date end,
                               Boolean enabled) {
        return repository.countFilter(enabled, user, message, channel, seen, messageBody, start, end);
    }

    @Transactional(readOnly = true)
    public Notification getNotificationById(Long id) {
        return repository.getNotificationById(id).orElseThrow(() -> new ResourceNotFoundException("Notification not found"));
    }


}
