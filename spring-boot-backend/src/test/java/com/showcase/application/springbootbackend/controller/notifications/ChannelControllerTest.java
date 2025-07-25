package com.showcase.application.springbootbackend.controller.notifications;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.showcase.application.springbootbackend.dto.request.notifications.ChannelRequest;
import com.showcase.application.springbootbackend.models.notifications.Channel;
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
public class ChannelControllerTest {

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
    void testPostThenGetBothReturning200() throws Exception {
        String json = new ObjectMapper().writeValueAsString(
                ChannelRequest.builder()
                        .name("Test Channel")
                        .build());

        MvcResult mvcResult = mockMvc.perform(
                post("/api/channel")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(json)
        ).andExpect(status().isOk()).andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        Channel response = objectMapper.readValue(jsonResponse, Channel.class);

        mockMvc.perform(get("/api/channel" + "?name=" + response.getName()))
                .andExpect(status().isOk());
    }

    @Test
    void testFindAllForUser() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/api/channel/user" + "?id=" + id))
                .andExpect(status().isOk());
    }

    @Test
    void testFindAll() throws Exception {
        mockMvc.perform(get("/api/channel/findall"))
                .andExpect(status().isOk());
    }
}
