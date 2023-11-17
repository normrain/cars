package com.example.hometask.domain.cars.service;

import com.example.hometask.domain.cars.entity.Car;
import com.example.hometask.domain.cars.reporitory.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<Car> getCarsForUser(Long userId) {
        return carRepository.findCarsByUserId(userId);
    }
}
