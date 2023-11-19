package com.example.hometask.integration;

import org.junit.ClassRule;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
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
public class CarControllerIntegrationTest {

    @ClassRule
    public static PostgreSQLContainer<PostgresTestContainer> postgreSQLContainer = PostgresTestContainer.getInstance();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllCars() throws Exception {
        mockMvc.perform(get("/api/v1/cars"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(9))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].make").value("Lada"))
                .andExpect(jsonPath("$[0].model").value("2101"))
                .andExpect(jsonPath("$[0].numberplate").value("123ASD"))
                .andExpect(jsonPath("$[8].id").value(9L))
                .andExpect(jsonPath("$[8].make").value("BMW"))
                .andExpect(jsonPath("$[8].model").value("740"))
                .andExpect(jsonPath("$[8].numberplate").value("112YUI"));
    }

    @Test
    public void testGetAllCarsFiltered() throws Exception {
        mockMvc.perform(get("/api/v1/cars")
                        .param("find", "Audi")
                        .param("sort", "make:desc")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(6L))
                .andExpect(jsonPath("$[0].make").value("Audi"))
                .andExpect(jsonPath("$[0].model").value("A6"))
                .andExpect(jsonPath("$[0].numberplate").value("997HHH"))
                .andExpect(jsonPath("$[1].id").value(8L))
                .andExpect(jsonPath("$[1].make").value("Audi"))
                .andExpect(jsonPath("$[1].model").value("A6"))
                .andExpect(jsonPath("$[1].numberplate").value("876OUI"));
    }

    @Test
    public void testGetSingleCar() throws Exception {
        Long carId = 1L;
        mockMvc.perform(get("/api/v1/cars/{carId}", carId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(carId))
                .andExpect(jsonPath("$.make").value("Lada"))
                .andExpect(jsonPath("$.model").value("2101"))
                .andExpect(jsonPath("$.numberplate").value("123ASD"));
    }

    @Test
    void testGetSingleCarWithInvalidParameter() throws Exception {

        mockMvc.perform(get("/api/v1/cars/{carId}", "a"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetSingleCarNotFound() throws Exception {

        mockMvc.perform(get("/api/v1/cars/{carId}", 50L))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
