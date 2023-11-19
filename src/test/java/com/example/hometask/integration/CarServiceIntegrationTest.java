package com.example.hometask.integration;

import com.example.hometask.domain.cars.api.model.CarResponse;
import com.example.hometask.domain.cars.repository.CarRepository;
import com.example.hometask.domain.cars.service.CarService;
import com.example.hometask.util.exception.EntityNotFoundException;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import utils.PostgresTestContainer;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
public class CarServiceIntegrationTest {

    @ClassRule
    public static PostgreSQLContainer<PostgresTestContainer> postgreSQLContainer = PostgresTestContainer.getInstance();

    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    @Test
    public void testGetAllCars() {
        List<CarResponse> cars = carService.getCars(null, null);

        assertNotNull(cars);

        assertEquals(cars.get(0).id().longValue(), 1L);
        assertEquals(cars.get(0).make(),"Lada");
        assertEquals(cars.get(0).model(),"2101");
        assertEquals(cars.get(0).numberplate(),"123ASD");

        assertEquals(cars.get(8).id().longValue(), 9L);
        assertEquals(cars.get(8).make(),"BMW");
        assertEquals(cars.get(8).model(),"740");
        assertEquals(cars.get(8).numberplate(),"112YUI");
    }
    @Test
    public void testGetAllCarsFiltered() {
        List<CarResponse> cars = carService.getCars("Audi", "make:desc");

        assertNotNull(cars);

        assertEquals(cars.get(0).id().longValue(), 6L);
        assertEquals(cars.get(0).make(),"Audi");
        assertEquals(cars.get(0).model(),"A6");
        assertEquals(cars.get(0).numberplate(),"997HHH");

        assertEquals(cars.get(1).id().longValue(), 8L);
        assertEquals(cars.get(1).make(),"Audi");
        assertEquals(cars.get(1).model(),"A6");
        assertEquals(cars.get(1).numberplate(),"876OUI");

    }

    @Test
    public void testGetSingleCar() throws EntityNotFoundException {
        CarResponse car = carService.getCarWithId(1L);

        assertEquals(car.id().longValue(), 1L);
        assertEquals(car.make(),"Lada");
        assertEquals(car.model(),"2101");
        assertEquals(car.numberplate(),"123ASD");
    }

    @Test
    public void testGetSingleCarNotFound() {
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            carService.getCarWithId(50L);
        });

        assertEquals(exception.getMessage(), "Car with id 50 not found");

    }
}
