package com.example.hometask.domain.cars.api.controller;


import com.example.hometask.domain.cars.api.model.CarResponse;
import com.example.hometask.domain.cars.service.CarService;
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
@RequestMapping(path = "/api/v1/cars")
public class CarController {

    private final CarService carService;
    @GetMapping()
    @Operation(summary = "Returns all Cars, with optional search term & sorting")
    public List<CarResponse> getAllCars(
            @RequestParam @Nullable String find,
            @RequestParam @Nullable String sort
    ) {
            return carService.getCars(find, sort);
    }

    @GetMapping(path = "/{carId}")
    @Operation(summary = "Returns a single car")
    public CarResponse getSingleCar(
            @PathVariable Long carId
    ) throws EntityNotFoundException {
        return carService.getCarWithId(carId);
    }

}
