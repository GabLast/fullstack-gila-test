package com.showcase.application.springbootbackend.bootstrap;

import com.showcase.application.springbootbackend.services.notifications.CategoryService;
import com.showcase.application.springbootbackend.services.notifications.ChannelService;
import com.showcase.application.springbootbackend.services.notifications.NotificationModuleService;
import com.showcase.application.springbootbackend.services.security.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
@RequiredArgsConstructor
@Slf4j
public class BootstrapData implements ApplicationRunner {

    private final UserService userService;
    private final ChannelService channelService;
    private final CategoryService categoryService;
    private final NotificationModuleService notificationModuleService;

    @Override
    public void run(ApplicationArguments args) {
        try {
            log.info("Loading Initial Values...");
            userService.boostrap();
            log.info("Loaded Users");
            channelService.boostrap();
            log.info("Loaded Channels");
            categoryService.boostrap();
            log.info("Loaded Categories");

            log.info("**************************************");
            notificationModuleService.generateUserChannelsAndSubscriptions();
            log.info("Loaded userId channels and subscriptions...");

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
