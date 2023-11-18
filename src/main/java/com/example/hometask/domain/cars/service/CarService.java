package com.example.hometask.domain.cars.service;

import com.example.hometask.domain.cars.api.model.CarResponse;
import com.example.hometask.domain.cars.entity.Car;
import com.example.hometask.domain.cars.reporitory.CarRepository;
import com.example.hometask.domain.users.entity.User;
import com.example.hometask.domain.users.service.UserService;
import com.example.hometask.util.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.hometask.util.Response.ResponseUtil.*;

@Service
@AllArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<CarResponse> getCarsForUser(Long userId){
        List<Car> cars = carRepository.findCarsByUserId(userId);
        return cars.stream().map(
                car -> CarResponse.builder()
                        .id(car.getId())
                        .make(car.getMake())
                        .model(car.getModel())
                        .numberplate(car.getNumberplate())
                        .build()
        ).collect(Collectors.toList());
    }

    public List<CarResponse> getCars(String find, String sort){
        List<Car> cars = (sort != null) ? carRepository.findAll(createSorting(sort)) : carRepository.findAll();

        if(find != null) {
            return mapFilteredCarEntityToResponse(cars, find);
        } else {
            return mapCarEntityToResponse(cars);
        }

    }

    public CarResponse getCarWithId(Long carId) throws EntityNotFoundException {
        Car car = carRepository.findById(carId).orElse(null);

        if(car == null) {
            throw new EntityNotFoundException("Car", carId);
        }

        return CarResponse.builder()
                .id(car.getId())
                .make(car.getMake())
                .model(car.getModel())
                .numberplate(car.getNumberplate())
                .build();
    }









}
