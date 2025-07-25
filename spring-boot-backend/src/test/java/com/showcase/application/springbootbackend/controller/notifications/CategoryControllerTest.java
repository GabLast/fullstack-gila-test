package com.showcase.application.springbootbackend.controller.notifications;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.showcase.application.springbootbackend.dto.request.notifications.CategoryRequest;
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
public class CategoryControllerTest {

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
                CategoryRequest.builder()
                        .name("Test Category")
                        .build());

        MvcResult mvcResult = mockMvc.perform(
                post("/api/category")
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(json)
        ).andExpect(status().isOk()).andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        Category response = objectMapper.readValue(jsonResponse, Category.class);

        mockMvc.perform(get("/api/category" + "?name=" + response.getName()))
                .andExpect(status().isOk());
    }

    @Test
    void testFindAllForUser() throws Exception {
        Long id = 1L;
        mockMvc.perform(get("/api/category/user" + "?id=" + id))
                .andExpect(status().isOk());
    }

    @Test
    void testFindAll() throws Exception {
        mockMvc.perform(get("/api/category/findall"))
                .andExpect(status().isOk());
    }
}
