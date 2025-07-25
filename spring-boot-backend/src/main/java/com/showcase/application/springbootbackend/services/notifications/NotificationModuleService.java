package com.showcase.application.springbootbackend.services.notifications;

import com.showcase.application.springbootbackend.dto.request.notifications.MessageRequest;
import com.showcase.application.springbootbackend.dto.request.notifications.NotificationSearchRequest;
import com.showcase.application.springbootbackend.dto.request.notifications.CheckNotificationRequest;
import com.showcase.application.springbootbackend.dto.response.notifications.MessageDto;
import com.showcase.application.springbootbackend.dto.response.notifications.MessageResponse;
import com.showcase.application.springbootbackend.dto.response.notifications.NotificationDto;
import com.showcase.application.springbootbackend.dto.response.notifications.NotificationResponse;
import com.showcase.application.springbootbackend.exceptions.ResourceNotFoundException;
import com.showcase.application.springbootbackend.models.notifications.Category;
import com.showcase.application.springbootbackend.models.notifications.Channel;
import com.showcase.application.springbootbackend.models.notifications.Message;
import com.showcase.application.springbootbackend.models.notifications.Notification;
import com.showcase.application.springbootbackend.models.notifications.UserCategory;
import com.showcase.application.springbootbackend.models.notifications.UserChannel;
import com.showcase.application.springbootbackend.models.security.User;
import com.showcase.application.springbootbackend.services.security.UserService;
import com.showcase.application.springbootbackend.utils.Utilities;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationModuleService {

    private final UserService userService;
    private final MessageService messageService;
    private final ChannelService channelService;
    private final CategoryService categoryService;
    private final UserChannelService userChannelService;
    private final UserCategoryService userCategoryService;
    private final NotificationService notificationService;

    public void generateUserChannelsAndSubscriptions() {

        userService.findAll().forEach(user -> {

            //generate some settings for each userId
            if (user.getName().equalsIgnoreCase("User 1")) {

                //User 1 gets 3 notifications if the categories are sports, finance or movies.
                generateUserChannelSettingForUser(user, Channel.ChannelType.SMS.getName());
                generateUserChannelSettingForUser(user, Channel.ChannelType.EMAIL.getName());
                generateUserChannelSettingForUser(user, Channel.ChannelType.PUSH_NOTIFICATION.getName());

                generateUserCategorySettingForUser(user, Category.CategoryType.SPORTS.getName());
                generateUserCategorySettingForUser(user, Category.CategoryType.FINANCE.getName());
                generateUserCategorySettingForUser(user, Category.CategoryType.MOVIES.getName());

            } else if (user.getName().equalsIgnoreCase("User 2")) {

                //User 2 gets 2 notifications if the categories are sports, finance or movies.
                generateUserChannelSettingForUser(user, Channel.ChannelType.SMS.getName());
                generateUserChannelSettingForUser(user, Channel.ChannelType.PUSH_NOTIFICATION.getName());

                generateUserCategorySettingForUser(user, Category.CategoryType.SPORTS.getName());
                generateUserCategorySettingForUser(user, Category.CategoryType.FINANCE.getName());
                generateUserCategorySettingForUser(user, Category.CategoryType.MOVIES.getName());

            } else if (user.getName().equalsIgnoreCase("User 3")) {

                //User 2 gets 2 notifications if the category is sports
                generateUserChannelSettingForUser(user, Channel.ChannelType.SMS.getName());
                generateUserChannelSettingForUser(user, Channel.ChannelType.EMAIL.getName());

                generateUserCategorySettingForUser(user, Category.CategoryType.SPORTS.getName());

            } else if (user.getName().equalsIgnoreCase("User 4")) {

                //User 4 gets 2 notifications if the categories are sports or movies
                generateUserChannelSettingForUser(user, Channel.ChannelType.EMAIL.getName());
                generateUserChannelSettingForUser(user, Channel.ChannelType.PUSH_NOTIFICATION.getName());

                generateUserCategorySettingForUser(user, Category.CategoryType.SPORTS.getName());
                generateUserCategorySettingForUser(user, Category.CategoryType.MOVIES.getName());

            } else if (user.getName().equalsIgnoreCase("User 5")) {

                //User 5 can not get notifications because he has not chosen any categories
                //to subscribe to.
                generateUserChannelSettingForUser(user, Channel.ChannelType.EMAIL.getName());
                //no category for this userId

            }
        });
    }

    private void generateUserCategorySettingForUser(User user, String categoryName) {

        Category category = categoryService.findByNameAndEnabled(categoryName, true);

        if (category == null) {
            return;
        }

        userCategoryService.saveAndFlush(UserCategory.builder()
                .user(user)
                .category(category)
                .build());
    }

    private void generateUserChannelSettingForUser(User user, String channelName) {

        Channel channel = channelService.findByNameAndEnabled(channelName, true);

        if (channel == null) {
            return;
        }

        userChannelService.saveAndFlush(UserChannel.builder()
                .user(user)
                .channel(channel)
                .build());
    }

    public NotificationDto getNotificationById(Long id) {
        return convertNotificationToDto(notificationService.getNotificationById(id));
    }

    public MessageDto getMessageById(Long id) {
        Message message = messageService.getMessageById(id);
        return MessageDto.builder()
                .id(message.getId())
                .message(message.getMessage())
                .category(message.getCategory().getName())
                .build();
    }

    public List<String> getChannelsForUser(Long userId) {
        User user = userService.get(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userChannelService
                .findAllByUserAndEnabled(user, true)
                .stream().map(it -> it.getChannel().getName()).collect(Collectors.toList());
    }

    public List<String> getCategoriesForUser(Long userId) {
        User user = userService.get(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userCategoryService
                .findAllByUserAndEnabled(user, true)
                .stream().map(it -> it.getCategory().getName()).collect(Collectors.toList());
    }

    public NotificationResponse findAllFilter(NotificationSearchRequest notificationSearchRequest) {

        Sort sort;
        String sortColumn = StringUtils.isBlank(notificationSearchRequest.sortColumn()) ? "dateCreated" : notificationSearchRequest.sortColumn();
        String sortOrder = StringUtils.isBlank(notificationSearchRequest.sortOrder()) ? "ASC" : notificationSearchRequest.sortOrder();
        int limit = notificationSearchRequest.limit() == null ? 20 : notificationSearchRequest.limit();
        int offset = notificationSearchRequest.offset() == null ? 0 : notificationSearchRequest.offset();

        if (sortOrder.equalsIgnoreCase("DESC")) {
            sort = Sort.by(Sort.Direction.DESC, sortColumn);
        } else {
            sort = Sort.by(Sort.Direction.ASC, sortColumn);
        }

        return NotificationResponse.builder()
                .data(notificationService.findAllFilter(
                                notificationSearchRequest.userId(),
                                notificationSearchRequest.messageId(),
                                notificationSearchRequest.channelId(),
                                notificationSearchRequest.seen(),
                                notificationSearchRequest.messageBody(),
                                notificationSearchRequest.start(),
                                notificationSearchRequest.end(),
                                notificationSearchRequest.enabled(),
                                limit,
                                offset,
                                sort)
                        .stream()
                        .map(this::convertNotificationToDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public Integer countFilter(NotificationSearchRequest notificationSearchRequest) {
        return notificationService.countFilter(
                notificationSearchRequest.userId(),
                notificationSearchRequest.messageId(),
                notificationSearchRequest.channelId(),
                notificationSearchRequest.seen(),
                notificationSearchRequest.messageBody(),
                notificationSearchRequest.start(),
                notificationSearchRequest.end(),
                notificationSearchRequest.enabled());
    }

    public MessageResponse forwardNotifications(MessageRequest messageRequest) {

        if (messageRequest.message().isBlank()) {
            throw new ResourceNotFoundException("The message can not be empty.");
        }

        String categoryName = Utilities.capitalizeEachWord(messageRequest.category());

        Category category = categoryService.findByNameAndEnabled(categoryName, true);
        Long categoryId;

        if (category == null) {
            category = categoryService.saveCategory(categoryName);
        }

        categoryId = category.getId();

        Message message = messageService.saveAndFlush(
                Message.builder()
                        .message(messageRequest.message())
                        .category(category)
                        .build());

        List<Notification> notificationList = new ArrayList<>();

        userService.findAll().forEach(user -> {

            boolean isUserSubscribedToCategory =
                    userCategoryService.findAllByUserAndEnabled(user, true).stream()
                            .anyMatch(userCategory -> userCategory.getCategory().getId().equals(categoryId));

            if (isUserSubscribedToCategory) {
                userChannelService.findAllByUserAndEnabled(user, true).forEach(channel -> {
                    notificationList.add(Notification.builder()
                            .user(user)
                            .channel(channel.getChannel())
                            .message(message)
                            .seen(false)
                            .build());
                });
            }
        });

        return MessageResponse.builder()
                .messageDto(MessageDto.builder()
                        .id(message.getId())
                        .category(message.getCategory().getName())
                        .message(message.getMessage())
                        .build())
                .data(notificationService.saveAllAndFlush(notificationList)
                        .stream()
                        .map(this::convertNotificationToDto)
                        .collect(Collectors.toList()))
                .build();
    }

    private NotificationDto convertNotificationToDto(Notification it) {
        return NotificationDto.builder()
                .id(it.getId())
                .user(it.getUser().getName())
                .userId(it.getUser().getId())
                .category(it.getMessage().getCategory().getName())
                .channel(it.getChannel().getName())
                .message(it.getMessage().getMessage())
                .dateCreated(it.getDateCreated())
                .seen(it.isSeen())
                .dateSeen(it.getDateSeen())
                .build();
    }

    public NotificationDto checkNotification(CheckNotificationRequest checkNotificationRequest) {

        User user = userService.findUserByIdOrMail(checkNotificationRequest.userDto().id(), checkNotificationRequest.userDto().email());
        if (user == null) {
            throw new ResourceNotFoundException("The selected userId does not exist: " + checkNotificationRequest.userDto().id() + "/" + checkNotificationRequest.userDto().email());
        }

        Optional<Notification> notification = notificationService.get(checkNotificationRequest.notificationId());
        if (notification.isEmpty()) {
            throw new ResourceNotFoundException("The notification with id [" + checkNotificationRequest.notificationId() + "] does not exist");
        }

        notification.get().setSeen(true);
        notification.get().setDateSeen(new Date());

        return convertNotificationToDto(notificationService.saveAndFlush(notification.get()));
    }
}
