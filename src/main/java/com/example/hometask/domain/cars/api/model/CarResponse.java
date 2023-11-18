package com.example.hometask.domain.cars.api.model;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

@Jacksonized
@Builder
public record CarResponse(
        Long id,
        String make,
        String model,
        String numberplate
) {}
