package com.example.hometask.integration;

import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import utils.PostgresTestContainer;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @ClassRule
    public static PostgreSQLContainer<PostgresTestContainer> postgreSQLContainer = PostgresTestContainer.getInstance();

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Teet Järveküla"))
                .andExpect(jsonPath("$[0].cars[0].id").value(1L))
                .andExpect(jsonPath("$[0].cars[1].id").value(2L))
                .andExpect(jsonPath("$[4].id").value(5L))
                .andExpect(jsonPath("$[4].name").value("Teet Kruus"))
                .andExpect(jsonPath("$[4].cars[0].id").value(9L));
    }

    @Test
    public void testGetAllUsersFiltered() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .param("find", "Teet")
                        .param("sort", "name:desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(5L))
                .andExpect(jsonPath("$[0].name").value("Teet Kruus"))
                .andExpect(jsonPath("$[0].cars[0].id").value(9L))
                .andExpect(jsonPath("$[1].id").value(1L))
                .andExpect(jsonPath("$[1].name").value("Teet Järveküla"))
                .andExpect(jsonPath("$[1].cars[0].id").value(1L))
                .andExpect(jsonPath("$[1].cars[1].id").value(2L));
    }

    @Test
    public void testGetSingleUser() throws Exception {
        Long userId = 3L;

        mockMvc.perform(get("/api/v1/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.name").value("Mati Kaal"))
                .andExpect(jsonPath("$.cars[0].id").value(5L))
                .andExpect(jsonPath("$.cars[1].id").value(6L));
    }

    @Test
    public void testGetCarsForUser() throws Exception {
        Long userId = 2L;

        mockMvc.perform(get("/api/v1/users/" + userId + "/cars")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(3L))
                .andExpect(jsonPath("$[0].make").value("Skoda"))
                .andExpect(jsonPath("$[0].model").value("Octavia"))
                .andExpect(jsonPath("$[0].numberplate").value("999GLF"))

                .andExpect(jsonPath("$[1].id").value(4L))
                .andExpect(jsonPath("$[1].make").value("Kia"))
                .andExpect(jsonPath("$[1].model").value("Sorento"))
                .andExpect(jsonPath("$[1].numberplate").value("555TFF"));
    }

    @Test
    void testGetSingleUserWithInvalidParameter() throws Exception {

        mockMvc.perform(get("/api/v1/users/{userId}", "a"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetSingleUserWithNotFound() throws Exception {

        mockMvc.perform(get("/api/v1/users/{userId}", 50L))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetCarsForUserWithInvalidParameter() throws Exception {

        mockMvc.perform(get("/api/v1/users/{userId}/cars", "a"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetCarsUserNotFound() throws Exception {

        mockMvc.perform(get("/api/v1/users/{userId}/cars", 50L))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
