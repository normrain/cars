package com.example.hometask.integration;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class CarControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Disabled
    public void testGetAllCars() throws Exception {
        mockMvc.perform(get("/api/v1/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
        // Add more assertions as needed based on your response structure
    }

    @Test
    @Disabled
    public void testGetSingleCar() throws Exception {
        Long carId = 1L;
        mockMvc.perform(get("/api/v1/cars/{carId}", carId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(carId));
        // Add more assertions as needed based on your response structure
    }

    // Add more integration tests for other scenarios and edge cases
}
