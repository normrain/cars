package com.example.hometask.unit;

import com.example.hometask.domain.cars.api.model.CarResponse;
import com.example.hometask.domain.cars.service.CarService;
import com.example.hometask.domain.users.api.controller.UserController;
import com.example.hometask.domain.users.api.model.UserResponse;
import com.example.hometask.domain.users.service.UserService;
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
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private CarService carService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @Test
    void testGetAllUsers() throws Exception {
        List<UserResponse> mockUserResponses = new ArrayList<>();
        mockUserResponses.add(new UserResponse(1L, "John Doe", new ArrayList<>())); // Assuming an empty list of cars

        when(userService.getUsersWithCars(anyString(), anyString())).thenReturn(mockUserResponses);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("John Doe"));
        // Add more assertions as needed
    }

    @Test
    void testGetSingleUser() throws Exception {
        Long userId = 1L;
        UserResponse mockUserResponse = new UserResponse(userId, "John Doe", new ArrayList<>()); // Assuming an empty list of cars

        when(userService.getUserWithId(userId)).thenReturn(mockUserResponse);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        mockMvc.perform(get("/api/v1/users/{userId}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
        // Add more assertions as needed
    }

    @Test
    void testGetCarsForUser() throws Exception {
        Long userId = 1L;
        List<CarResponse> mockCarResponses = new ArrayList<>();
        mockCarResponses.add(new CarResponse(1L, "Toyota", "Corolla", "ABC123"));

        when(carService.getCarsForUser(userId)).thenReturn(mockCarResponses);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        mockMvc.perform(get("/api/v1/users/{userId}/cars", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].make").value("Toyota"));
        // Add more assertions as needed
    }

    // Add more test cases to cover different scenarios and edge cases
}
