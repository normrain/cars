package com.example.hometask.integration;

import com.example.hometask.domain.cars.service.CarService;
import com.example.hometask.domain.users.api.model.UserResponse;
import com.example.hometask.domain.users.repository.UserRepository;
import com.example.hometask.domain.users.service.UserService;
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
public class UserServiceIntegrationTest {

    @ClassRule
    public static PostgreSQLContainer<PostgresTestContainer> postgreSQLContainer = PostgresTestContainer.getInstance();

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CarService carService;

    @Autowired
    private UserService userService;

    @Test
    public void testGetAllUsers() {
        List<UserResponse> users = userService.getUsersWithCars(null, null);

        assertNotNull(users);

        assertEquals(users.get(0).id().longValue(), 1L);
        assertEquals(users.get(0).name(),"Teet Järveküla");
        assertEquals(users.get(0).cars().get(0).id().longValue(),1L);
        assertEquals(users.get(0).cars().get(1).id().longValue(),2L);

        assertEquals(users.get(4).id().longValue(), 5L);
        assertEquals(users.get(4).name(),"Teet Kruus");
        assertEquals(users.get(4).cars().get(0).id().longValue(),9L);

    }

    @Test
    public void testGetAllUsersFiltered() {
        List<UserResponse> users = userService.getUsersWithCars("Teet", "name:desc");

        assertNotNull(users);

        assertEquals(users.get(0).id().longValue(), 5L);
        assertEquals(users.get(0).name(),"Teet Kruus");
        assertEquals(users.get(0).cars().get(0).id().longValue(),9L);

        assertEquals(users.get(1).id().longValue(), 1L);
        assertEquals(users.get(1).name(),"Teet Järveküla");
        assertEquals(users.get(1).cars().get(0).id().longValue(),1L);
        assertEquals(users.get(1).cars().get(1).id().longValue(),2L);
    }

    @Test
    public void testGetSingleUser() throws EntityNotFoundException {

        UserResponse user = userService.getUserWithId(3L);

        assertNotNull(user);

        assertEquals(user.id().longValue(), 3L);
        assertEquals(user.name(),"Mati Kaal");
        assertEquals(user.cars().get(0).id().longValue(),5L);
        assertEquals(user.cars().get(1).id().longValue(),6L);
    }

    @Test
    public void testGetSingleUserNotFound() {
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            userService.getUserWithId(50L);
        });

        assertEquals(exception.getMessage(), "User with id 50 not found");
    }

}
