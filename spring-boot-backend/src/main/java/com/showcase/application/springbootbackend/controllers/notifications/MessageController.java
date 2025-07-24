package com.showcase.application.springbootbackend.controllers.notifications;

import com.showcase.application.springbootbackend.dto.request.notifications.MessageRequest;
import com.showcase.application.springbootbackend.services.notifications.MessageService;
import com.showcase.application.springbootbackend.services.notifications.NotificationModuleService;
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
@RequestMapping("api/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final NotificationModuleService notificationModuleService;

    @GetMapping
    public ResponseEntity<?> get(@RequestParam("id") Long id) {
        return new ResponseEntity<>(notificationModuleService.getMessageById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody MessageRequest messageRequest) {
        return new ResponseEntity<>(notificationModuleService.forwardNotifications(messageRequest), HttpStatus.OK);
    }

    @GetMapping("/findall")
    public ResponseEntity<?> findall() {
        return new ResponseEntity<>(messageService.findAllByEnabled(true), HttpStatus.OK);
    }
}
