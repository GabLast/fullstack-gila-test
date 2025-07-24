package com.showcase.application.springbootbackend.controllers.notifications;

import com.showcase.application.springbootbackend.dto.request.notifications.ChannelRequest;
import com.showcase.application.springbootbackend.services.notifications.ChannelService;
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
@RequestMapping("api/channel")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;
    private final NotificationModuleService notificationModuleService;

    @GetMapping
    public ResponseEntity<?> get(@RequestParam("name") String name) {
        return new ResponseEntity<>(channelService.findByNameAndEnabled(name, true), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody ChannelRequest channelRequest) {
        return new ResponseEntity<>(channelService.saveChannel(channelRequest.name()), HttpStatus.OK);
    }

    @GetMapping("/findall")
    public ResponseEntity<?> findAll() {
        return new ResponseEntity<>(channelService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserChannels(@RequestParam("id") Long userId) {
        return new ResponseEntity<>(notificationModuleService.getChannelsForUser(userId), HttpStatus.OK);
    }
}
