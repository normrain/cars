package com.example.hometask.domain.users.api.model;

import com.example.hometask.domain.cars.api.model.CarResponse;
import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Jacksonized
@Builder
public record UserResponse(
        Long id,
        String name,
        List<CarResponse> cars
) { }
