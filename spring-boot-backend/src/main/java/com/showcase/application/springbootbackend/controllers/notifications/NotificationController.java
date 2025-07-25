package com.showcase.application.springbootbackend.controllers.notifications;

import com.showcase.application.springbootbackend.dto.request.notifications.CheckNotificationRequest;
import com.showcase.application.springbootbackend.dto.request.notifications.NotificationSearchRequest;
import com.showcase.application.springbootbackend.services.notifications.NotificationModuleService;
import com.showcase.application.springbootbackend.services.notifications.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationModuleService notificationModuleService;

    @GetMapping
    public ResponseEntity<?> get(@RequestParam("id") Long id) {
        return new ResponseEntity<>(
                notificationModuleService.getNotificationById(id), HttpStatus.OK);
    }

    @GetMapping("/findall")
    public ResponseEntity<?> findAll(NotificationSearchRequest notificationSearchRequest) {
        return new ResponseEntity<>(
                notificationModuleService.findAllFilter(notificationSearchRequest), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<?> count(NotificationSearchRequest notificationSearchRequest) {
        return new ResponseEntity<>(
                notificationModuleService.countFilter(notificationSearchRequest), HttpStatus.OK);
    }

    @PostMapping("/checknotification")
    public ResponseEntity<?> count(@RequestBody CheckNotificationRequest request) {
        return new ResponseEntity<>(notificationModuleService.checkNotification(request), HttpStatus.OK);
    }
}
