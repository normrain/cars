package com.example.hometask.unit;

import com.example.hometask.domain.cars.api.controller.CarController;
import com.example.hometask.domain.cars.api.model.CarResponse;
import com.example.hometask.domain.cars.service.CarService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CarControllerTest {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    private MockMvc mockMvc;

    @Test
    void testGetAllCars() throws Exception {
        List<CarResponse> mockCarResponses = new ArrayList<>();
        mockCarResponses.add(new CarResponse(1L, "Toyota", "Corolla", "ABC123"));
        mockCarResponses.add(new CarResponse(2L, "Honda", "Civic", "XYZ789"));

        when(carService.getCars(anyString(), anyString())).thenReturn(mockCarResponses);

        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();

        mockMvc.perform(get("/api/v1/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].make").value("Toyota"))
                .andExpect(jsonPath("$[1].model").value("Civic"));
        // Add more assertions as needed
    }

    @Test
    void testGetSingleCar() throws Exception {
        Long carId = 1L;
        CarResponse mockCarResponse = new CarResponse(carId, "Toyota", "Corolla", "ABC123");

        when(carService.getCarWithId(carId)).thenReturn(mockCarResponse);

        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();

        mockMvc.perform(get("/api/v1/cars/{carId}", carId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.make").value("Toyota"))
                .andExpect(jsonPath("$.numberplate").value("ABC123"));
        // Add more assertions as needed
    }

    // Add more test cases to cover different scenarios and edge cases
}
