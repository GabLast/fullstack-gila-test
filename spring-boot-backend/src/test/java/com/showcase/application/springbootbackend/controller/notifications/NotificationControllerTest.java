package com.showcase.application.springbootbackend.controller.notifications;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.showcase.application.springbootbackend.dto.request.notifications.CheckNotificationRequest;
import com.showcase.application.springbootbackend.dto.request.notifications.MessageRequest;
import com.showcase.application.springbootbackend.dto.request.security.UserDto;
import com.showcase.application.springbootbackend.dto.response.notifications.MessageResponse;
import com.showcase.application.springbootbackend.dto.response.notifications.NotificationDto;
import com.showcase.application.springbootbackend.models.notifications.Category;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static ObjectMapper objectMapper;

    @BeforeAll
    static void init() {
        objectMapper = new ObjectMapper();
    }

    @AfterAll
    static void finish() {
        objectMapper = null;
    }

    @Test
    void postMessageReturning200StatusCodeAndGetOneNotification() throws Exception {

        String json = new ObjectMapper().writeValueAsString(
                MessageRequest.builder()
                        .message("My test message for movies")
                        .category(Category.CategoryType.MOVIES.getName())
                        .build());

        MvcResult mvcResult = mockMvc.perform(
                post("/api/message")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(json)
        ).andExpect(status().isOk()).andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        MessageResponse response = objectMapper.readValue(jsonResponse, MessageResponse.class);

        NotificationDto anyElement = response.data().stream().findAny().orElseThrow(RuntimeException::new);

        mockMvc.perform(get("/api/notification" + "?id=" + anyElement.id()))
                .andExpect(status().isOk());
    }

    @Test
    void getNotificationExpectNotFound() throws Exception {
        mockMvc.perform(get("/api/notification" + "?id=" + 99999999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindAll() throws Exception {
        mockMvc.perform(get("/api/notification/findall"))
                .andExpect(status().isOk());
    }

    @Test
    void testCount() throws Exception {
        mockMvc.perform(get("/api/notification/count"))
                .andExpect(status().isOk());
    }

    @Test
    void postCheckNotificationReturning200AndGetByIdReturning200() throws Exception {

        Long id = 1L;

        String json = new ObjectMapper().writeValueAsString(
                CheckNotificationRequest.builder()
                        .userDto(UserDto.builder().id(1L).email("mail1@mail.com").build())
                        .notificationId(id)
                        .build());

        MvcResult mvcResult = mockMvc.perform(
                post("/api/notification/checknotification")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(json)
        ).andExpect(status().isOk()).andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        NotificationDto response = objectMapper.readValue(jsonResponse, NotificationDto.class);

        mockMvc.perform(get("/api/notification" + "?id=" + response.id()))
                .andExpect(status().isOk());
    }

}
