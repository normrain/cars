package com.example.hometask.domain.cars.service;

import com.example.hometask.domain.cars.api.model.CarResponse;
import com.example.hometask.domain.cars.entity.Car;
import com.example.hometask.domain.cars.repository.CarRepository;
import com.example.hometask.util.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.hometask.util.Sorting.SortingUtil.createSorting;


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

    private List<CarResponse> mapCarEntityToResponse(List<Car> cars) {
        return cars.stream().map(
                car -> CarResponse.builder()
                        .id(car.getId())
                        .make(car.getMake())
                        .model(car.getModel())
                        .numberplate(car.getNumberplate())
                        .build()
        ).collect(Collectors.toList());
    }

    private List<CarResponse> mapFilteredCarEntityToResponse(List<Car> cars, String find) {
        return cars.stream()
                .filter(car -> car.getMake().contains(find) ||
                        car.getModel().contains(find)
                        || car.getNumberplate().contains(find))
                .map(
                        car -> CarResponse.builder()
                                .id(car.getId())
                                .make(car.getMake())
                                .model(car.getModel())
                                .numberplate(car.getNumberplate())
                                .build()
                ).collect(Collectors.toList());
    }
}
