package com.example.hometask.unit;

import com.example.hometask.domain.cars.api.model.CarResponse;
import com.example.hometask.domain.cars.entity.Car;
import com.example.hometask.domain.cars.repository.CarRepository;
import com.example.hometask.domain.cars.service.CarService;
import com.example.hometask.util.exception.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static utils.TestObjectCreator.createNewCar;

@Tag("unit")
@RunWith(MockitoJUnitRunner.class)
public class CarServiceTest {
    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCarsForUser() {
        Long userId = 1L;
        List<Car> mockCars = List.of(
                createNewCar(1L,"Toyota", "Corolla", "ABC123"),
                createNewCar(2L, "Honda", "Civic", "XYZ789")
        );

        when(carRepository.findCarsByUserId(userId)).thenReturn(mockCars);

        List<CarResponse> result = carService.getCarsForUser(userId);

        assertEquals(2, result.size());
        assertEquals("Toyota", result.get(0).make());
        assertEquals("Civic", result.get(1).model());
    }

    @Test
    void testGetCars() {
        when(carRepository.findAll()).thenReturn(List.of(
                createNewCar(1L,"Toyota", "Corolla", "ABC123"),
                createNewCar(2L, "Honda", "Civic", "XYZ789")
        ));

        List<CarResponse> result = carService.getCars(null, null);

        assertNotNull(result);
        assertEquals("Toyota", result.get(0).make());
        assertEquals("Civic", result.get(1).model());
    }

    @Test
    void testGetCarWithId_ExistingCar() throws EntityNotFoundException {
        Long carId = 1L;
        Car mockCar = createNewCar(carId, "Toyota", "Corolla", "ABC123");

        when(carRepository.findById(carId)).thenReturn(Optional.of(mockCar));

        CarResponse result = carService.getCarWithId(carId);

        assertNotNull(result);
        assertEquals("Toyota", result.make());
    }

    @Test
    void testGetCarWithId_NonExistingCar() {
        Long carId = 1L;

        when(carRepository.findById(carId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> carService.getCarWithId(carId));
    }
}
