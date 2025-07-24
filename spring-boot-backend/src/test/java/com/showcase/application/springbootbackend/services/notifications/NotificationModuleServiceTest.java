package com.showcase.application.springbootbackend.services.notifications;

import com.showcase.application.springbootbackend.dto.request.notifications.CheckNotificationRequest;
import com.showcase.application.springbootbackend.dto.request.notifications.MessageRequest;
import com.showcase.application.springbootbackend.dto.request.notifications.NotificationSearchRequest;
import com.showcase.application.springbootbackend.dto.request.security.UserDto;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotificationModuleServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private MessageService messageService;
    @Mock
    private ChannelService channelService;
    @Mock
    private CategoryService categoryService;
    @Mock
    private UserChannelService userChannelService;
    @Mock
    private UserCategoryService userCategoryService;
    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationModuleService service;

    @Test
    void testGetNotificationById() {
        Long id = 1L;
        Notification data = Notification.builder()
                .user(User.builder().name("user 1").phoneNumber("123-123-1234").email("mail1@mail.com").build())
                .channel(Channel.builder().name("my channel").build())
                .message(Message.builder().category(Category.builder().name("category").build()).message("my message").build())
                .build();
        data.setId(id);

        when(notificationService.getNotificationById(id)).thenReturn(data);

        NotificationDto test = service.getNotificationById(id);

        Assertions.assertNotNull(test);
    }

    @Test
    void testGetMessageById() {
        Long id = 1L;
        Message data = Message.builder()
                .category(Category.builder().name("my category").build())
                .message("my message")
                .build();
        data.setId(id);

        when(messageService.getMessageById(id)).thenReturn(data);

        MessageDto test = service.getMessageById(id);

        Assertions.assertNotNull(test);
    }

    @Test
    void testGetChannelsForUser() {

        Long id = 1L;
        User user = User.builder().name("user 1").phoneNumber("123-123-1234").email("mail1@mail.com").build();
        user.setId(id);

        List<UserChannel> list = new ArrayList<>();
        list.add(UserChannel.builder()
                .user(user).channel(Channel.builder().name("channel 1").build())
                .build());
        list.add(UserChannel.builder()
                .user(user).channel(Channel.builder().name("channel 2").build())
                .build());

        when(userService.get(id)).thenReturn(Optional.of(user));
        when(userChannelService.findAllByUserAndEnabled(user, true)).thenReturn(list);

        List<String> test = service.getChannelsForUser(id);

        Assertions.assertNotNull(test);
    }

    @Test
    void testGetCategoriesForUser() {

        Long id = 1L;
        User user = User.builder().name("user 1").phoneNumber("123-123-1234").email("mail1@mail.com").build();
        user.setId(id);

        List<UserCategory> list = new ArrayList<>();
        list.add(UserCategory.builder()
                .user(user).category(Category.builder().name("category 1").build())
                .build());
        list.add(UserCategory.builder()
                .user(user).category(Category.builder().name("category 2").build())
                .build());

        when(userService.get(id)).thenReturn(Optional.of(user));
        when(userCategoryService.findAllByUserAndEnabled(user, true)).thenReturn(list);

        List<String> test = service.getCategoriesForUser(id);

        Assertions.assertNotNull(test);
    }

    @Test
    void testFindAllFilter() {
        List<Notification> list = new ArrayList<>();
        list.add(Notification.builder()
                .user(User.builder().name("user 1").phoneNumber("123-123-1234").email("mail1@mail.com").build())
                .channel(Channel.builder().name("SMS").build())
                .message(Message.builder().category(Category.builder().name("category").build()).message("my message").build())
                .build());
        list.add(Notification.builder()
                .user(User.builder().name("user 2").phoneNumber("222-222-1234").email("mail2@mail.com").build())
                .channel(Channel.builder().name("SMS").build())
                .message(Message.builder().category(Category.builder().name("category 2").build()).message("my message 2").build())
                .build());

        when(notificationService.findAllFilter(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyInt(), Mockito.anyInt(), Mockito.any()))
                .thenReturn(list);

        NotificationSearchRequest request = NotificationSearchRequest.builder()
                .limit(2)
                .offset(0)
                .build();
        NotificationResponse test = service.findAllFilter(request);

        Assertions.assertNotNull(test);
    }

    @Test
    void testCountAllFilter() {
        NotificationSearchRequest request = NotificationSearchRequest.builder()
                .build();

        when(notificationService.countFilter(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(0);

        Integer test = service.countFilter(request);

        Assertions.assertNotNull(test);
    }

    @Test
    void testForwardNotifications() {
        String categoryName = "Sports";
        String messageBody = "My message";

        MessageRequest messageRequest = MessageRequest.builder()
                .category(categoryName)
                .message(messageBody)
                .build();

        Category category = Category.builder()
                .name(messageRequest.category())
                .build();
        category.setId(1L);

        when(categoryService.findByNameAndEnabled(messageRequest.category(), true)).thenReturn(category);

        Message message = Message.builder()
                .category(categoryService.findByNameAndEnabled(messageRequest.category(), true))
                .message(messageRequest.message())
                .build();
        message.setId(1L);
        when(messageService.saveAndFlush(Mockito.any())).thenReturn(message);

        User user = User.builder().name("user 1").phoneNumber("123-123-1234").email("mail1@mail.com").build();
        user.setId(1L);
        when(userService.findAll()).thenReturn(List.of(user));

        List<UserCategory> userCategories = new ArrayList<>();
        userCategories.add(UserCategory.builder()
                .user(user).category(Category.builder().name("category 1").build())
                .build());
        userCategories.add(UserCategory.builder()
                .user(user).category(Category.builder().name("category 2").build())
                .build());
        lenient().when(userCategoryService.findAllByUserAndEnabled(user, true)).thenReturn(userCategories);

        List<UserChannel> userChannels = new ArrayList<>();
        userChannels.add(UserChannel.builder()
                .user(user).channel(Channel.builder().name("channel 1").build())
                .build());
        userChannels.add(UserChannel.builder()
                .user(user).channel(Channel.builder().name("channel 2").build())
                .build());
        lenient().when(userChannelService.findAllByUserAndEnabled(user, true)).thenReturn(userChannels);

        List<Notification> notificationList = new ArrayList<>();
        notificationList.add(Notification.builder()
                .user(User.builder().name("user 1").phoneNumber("123-123-1234").email("mail1@mail.com").build())
                .channel(Channel.builder().name("SMS").build())
                .message(message)
                .build());
        notificationList.add(Notification.builder()
                .user(User.builder().name("user 2").phoneNumber("222-222-1234").email("mail2@mail.com").build())
                .channel(Channel.builder().name("E-mail").build())
                .message(message)
                .build());
        when(notificationService.saveAllAndFlush(Mockito.any())).thenReturn(notificationList);

        MessageResponse test = service.forwardNotifications(messageRequest);
        Assertions.assertNotNull(test);

        MessageRequest request2 = MessageRequest.builder()
                .category(categoryName)
                .message("")
                .build();
        Assertions.assertThrows(ResourceNotFoundException.class, () -> service.forwardNotifications(request2));
    }

    @Test
    void testCheckNotification() {
        User user = User.builder().name("user 1").phoneNumber("123-123-1234").email("mail1@mail.com").build();
        user.setId(1L);

        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();

        CheckNotificationRequest checkNotificationRequest = CheckNotificationRequest.builder()
                .notificationId(1L)
                .userDto(userDto)
                .build();

        when(userService.findUserByIdOrMail(Mockito.any(), Mockito.any())).thenReturn(user);

        Notification notification = Notification.builder()
                .user(User.builder().name("user 1").phoneNumber("123-123-1234").email("mail1@mail.com").build())
                .channel(Channel.builder().name("SMS").build())
                .message(Message.builder().message("message").category(Category.builder().name("category").build()).build())
                .build();
        notification.setId(1L);

        when(notificationService.get(Mockito.any())).thenReturn(Optional.of(notification));

        notification.setSeen(true);
        notification.setDateSeen(new Date());
        when(notificationService.saveAndFlush(Mockito.any())).thenReturn(notification);

        NotificationDto test = service.checkNotification(checkNotificationRequest);

        Assertions.assertNotNull(test);
        Assertions.assertNotNull(test.dateSeen());
        Assertions.assertTrue(test.seen());
    }
}
