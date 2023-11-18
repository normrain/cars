package com.example.hometask.unit;

import com.example.hometask.domain.cars.api.model.CarResponse;
import com.example.hometask.domain.cars.service.CarService;
import com.example.hometask.domain.users.api.model.UserResponse;
import com.example.hometask.domain.users.entity.User;
import com.example.hometask.domain.users.repository.UserRepository;
import com.example.hometask.domain.users.service.UserService;
import com.example.hometask.util.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static utils.TestObjectCreator.createNewUser;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CarService carService;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserWithId_ExistingUser() throws EntityNotFoundException {
        Long userId = 1L;
        User mockUser = createNewUser(userId, "John Doe");
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        List<CarResponse> mockCarResponses = new ArrayList<>();
        mockCarResponses.add(new CarResponse(1L, "Toyota", "Corolla", "ABC123"));
        mockCarResponses.add(new CarResponse(2L, "Honda", "Civic", "XYZ789"));

        when(carService.getCarsForUser(userId)).thenReturn(mockCarResponses);

        UserResponse result = userService.getUserWithId(userId);

        assertNotNull(result);
        assertEquals("John Doe", result.name());
        assertEquals(2, result.cars().size());
        // Add more assertions as needed
    }

    @Test
    void testGetUserWithId_NonExistingUser() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getUserWithId(userId));
    }

    @Test
    void testGetUsersWithCars() {
        when(userRepository.findAll()).thenReturn(new ArrayList<>());

        List<UserResponse> result = userService.getUsersWithCars(null, null);

        assertNotNull(result);
        assertEquals(0, result.size());
        // Add more assertions as needed
    }

    // Add more test cases to cover different scenarios and edge cases
}
