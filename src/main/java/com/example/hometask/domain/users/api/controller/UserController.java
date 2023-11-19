package com.example.hometask.domain.users.api.controller;

import com.example.hometask.domain.cars.api.model.CarResponse;
import com.example.hometask.domain.users.api.model.UserResponse;
import com.example.hometask.domain.cars.service.CarService;
import com.example.hometask.domain.users.service.UserService;
import com.example.hometask.util.exception.EntityNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@Validated
@RestController
@ControllerAdvice
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/users")
public class UserController {

    private final UserService userService;
    private final CarService carService;

    @GetMapping()
    @Operation(summary = "Returns all users with cars, with optional search term and sorting")
    public List<UserResponse> getAllUsers(
            @RequestParam @Nullable String find,
            @RequestParam @Nullable String sort
    ) {
        return userService.getUsersWithCars(find, sort);
    }

    @GetMapping(path = "/{userId}")
    @Operation(summary = "Returns a single user")
    public UserResponse getSingleUser(
            @PathVariable Long userId
    ) throws EntityNotFoundException {
        return userService.getUserWithId(userId);
    }

    @GetMapping(path = "/{userId}/cars")
    @Operation(summary = "Returns cars of a specific user")
    public List<CarResponse> getCarsForUser(
            @PathVariable Long userId
    ) throws EntityNotFoundException {
        userService.getUserWithId(userId);
        return carService.getCarsForUser(userId);
    }

}
