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
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

        when(carService.getCars("test", "name:asc")).thenReturn(mockCarResponses);

        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();

        mockMvc.perform(get("/api/v1/cars?find=test&sort=name:asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].make").value("Toyota"))
                .andExpect(jsonPath("$[0].model").value("Corolla"))
                .andExpect(jsonPath("$[0].numberplate").value("ABC123"))
                .andExpect(jsonPath("$[1].make").value("Honda"))
                .andExpect(jsonPath("$[1].model").value("Civic"))
                .andExpect(jsonPath("$[1].numberplate").value("XYZ789"));
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
                .andExpect(jsonPath("$.numberplate").value("ABC123"))
                .andExpect(jsonPath("$.numberplate").value("ABC123"));
    }

    @Test
    void testGetSingleCarWithInvalidParameter() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();

        mockMvc.perform(get("/api/v1/cars/{carId}", "a"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

}
